package ru.itmo.highload.storroom.files.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String username;
    private String filename;

    private FilePart file;

    public FileDTO withObjectName(String objectName) {
        int separator = objectName.indexOf("/");
        String username = objectName.substring(0, separator);
        String filename = objectName.substring(separator+1);
        this.username = username;
        this.filename = filename;
        return this;
    }

    public String getObjectName() {
        return username + "/" + filename;
    }

    public String getRealFilename() {
        return this.getFilename() == null || this.getFilename().isEmpty()
                ? this.file.filename() : this.getFilename();
    }

}
