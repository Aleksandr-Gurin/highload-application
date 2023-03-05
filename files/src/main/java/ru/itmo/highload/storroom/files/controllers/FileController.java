package ru.itmo.highload.storroom.files.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.dtos.FileDTO;
import ru.itmo.highload.storroom.files.services.FileService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<FileDTO>> getFiles() {
        return fileService.getFiles();
    }
    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<FileDTO>> getFilesByUser(@PathVariable String username) {
        return fileService.getFilesByUser(username);
    }

    @GetMapping("/{username}/{filename}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Mono<InputStreamResource>> getFile(@PathVariable String username, @PathVariable String filename) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(fileService.getFile(username, filename));
    }

    @PostMapping("/{username}/{filename}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<FileDTO> upload(@PathVariable String username,
                                @PathVariable String filename,
                                @RequestPart(name = "file") FilePart filePart) {
        return fileService.upload(username, filename, filePart);
    }

    @DeleteMapping("/{username}/{filename}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Boolean> deleteFile(@PathVariable String username, @PathVariable String filename) {
        return fileService.deleteFile(username, filename);
    }

}