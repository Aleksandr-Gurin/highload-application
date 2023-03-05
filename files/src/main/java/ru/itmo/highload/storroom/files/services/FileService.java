package ru.itmo.highload.storroom.files.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.dtos.FileDTO;
import ru.itmo.highload.storroom.files.dtos.NotificationMessage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final MinioService minioService;
    private final NotificationService notificationService;

    public Mono<List<FileDTO>> getFiles() {
        return minioService.getListObjects("");
    }


    public Mono<List<FileDTO>> getFilesByUser(String username) {
        return minioService.getListObjects(username);
    }

    public Mono<InputStreamResource> getFile(String username, String filename) {
        return minioService.download(username, filename);
    }

    public Mono<FileDTO> upload(String username,
                                String filename,
                                FilePart filePart) {
        FileDTO file = new FileDTO(username, filename, filePart);
        return minioService.uploadFile(file)
                .doOnNext(resDto -> sendUploadNotification(resDto).subscribe());
    }

    private Mono<Void> sendUploadNotification(FileDTO file) {
        boolean isOk = file.getObjectName() != null | !file.getObjectName().isEmpty();
        String okMsg = String.format("File %s was uploaded", file.getRealFilename());
        String errMsg = String.format("Err: File %s was NOT uploaded", file.getRealFilename());

        NotificationMessage message =
                NotificationMessage.builder()
                        .username(file.getUsername())
                        .timestamp(LocalDateTime.now())
                        .message(isOk ? okMsg : errMsg)
                        .build();
        return notificationService.sendNotification(message);
    }

    public Mono<Boolean> deleteFile(@PathVariable String username, @PathVariable String filename) {
        String objectName = username + "/" + filename;
        return minioService.deleteObject(objectName)
                .doOnNext(isDeleted -> sendDeleteNotification(username, filename, isDeleted).subscribe());
    }

    private Mono<Void> sendDeleteNotification(String username, String filename, boolean isDeleted) {
        String okMsg = String.format("File %s was deleted", filename);
        String errMsg = String.format("Err: File %s was NOT deleted", filename);

        NotificationMessage message =
                NotificationMessage.builder()
                        .username(username)
                        .timestamp(LocalDateTime.now())
                        .message(isDeleted ? okMsg : errMsg)
                        .build();
        return notificationService.sendNotification(message);
    }
}
