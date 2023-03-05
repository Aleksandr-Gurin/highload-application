package ru.itmo.highload.storroom.files.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.dtos.FileDTO;
import ru.itmo.highload.storroom.files.utils.MinioAdapter;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioAdapter minioAdapter;

    public Mono<List<FileDTO>> getListObjects(String username) {
        String prefix = username == null || username.isEmpty() ? "" : username;
        return  minioAdapter.getListObjects(prefix);
    }

    @SneakyThrows
    public Mono<FileDTO> uploadFile(FileDTO file) {
        if(file.getUsername() == null) throw new IllegalArgumentException();
        return  minioAdapter.uploadFile(file);
    }

    public Mono<InputStreamResource> download(String username, String filename) {
        String objectName = username + "/" + filename;
        return minioAdapter.download(objectName);
    }

    public Mono<Boolean> deleteObject(String name) {
        return minioAdapter.deleteObject(name);
    }
}