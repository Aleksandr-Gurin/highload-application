package ru.itmo.highload.storroom.files.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationMessage {
    String username;
    LocalDateTime timestamp;
    String message;
}
