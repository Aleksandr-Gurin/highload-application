package ru.itmo.highload.storroom.files.utils;

import io.minio.*;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.itmo.highload.storroom.files.dtos.FileDTO;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MinioAdapter {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket.name}")
    String defaultBucketName;


    final String defaultFilepath = "uploads/";



    public Mono<List<FileDTO>> getListObjects(String prefix) {
        return Mono.fromCallable(() -> {
            List<FileDTO> objects = new ArrayList<>();
            try {
                Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(defaultBucketName)
                        .recursive(true)
                        .prefix(prefix)
                        .build());
                for (Result<Item> item : result) {
                    objects.add(new FileDTO().withObjectName(item.get().objectName()));
                }
                return objects;
            } catch (Exception e) {
                log.error("Happened error when get list objects from minio: ", e);
            }

            return objects;
        }).subscribeOn(Schedulers.boundedElastic());
    }


    @SneakyThrows
    public Mono<FileDTO> uploadFile(FileDTO file) {
        return Mono.just(file.getFile()).map(multipartFile -> {
            long startMillis = System.currentTimeMillis();
            String filename = file.getRealFilename();
            File dir = new File(defaultFilepath + file.getUsername());
            dir.mkdirs();
            File temp = new File(defaultFilepath + file.getUsername() + "/" + filename);
            temp.canWrite();
            temp.canRead();
            try {
                System.out.println(temp.getAbsolutePath());
                // blocking to complete io operation
                multipartFile.transferTo(temp).block();
                UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                        .bucket(defaultBucketName)
                        .object(file.getUsername() + "/" + filename)
                        .filename(temp.getAbsolutePath())
                        .build();

                ObjectWriteResponse response = minioClient.uploadObject(uploadObjectArgs);
                log.info("upload file execution time {} ms", System.currentTimeMillis() - startMillis);
                return new FileDTO().withObjectName(response.object());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                temp.delete();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<InputStreamResource> download(String name) {
        return Mono.fromCallable(() -> {
            InputStream response = minioClient.getObject(GetObjectArgs.builder().bucket(defaultBucketName).object(name).build());
            return new InputStreamResource(response);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Boolean> deleteObject(String name) {
        return Mono.fromCallable(() -> {
            try {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(defaultBucketName)
                        .object(name)
                        .build());
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }).onErrorReturn(false).subscribeOn(Schedulers.boundedElastic());
    }

}