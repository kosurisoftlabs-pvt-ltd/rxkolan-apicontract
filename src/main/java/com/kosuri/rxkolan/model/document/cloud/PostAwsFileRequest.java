package com.kosuri.rxkolan.model.document.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostAwsFileRequest extends PostCloudFileRequest {
    private String bucketName;

    @NotNull
    private Long fileLength;

    public PostAwsFileRequest(String bucketName, String filePath, InputStream fileStream, Long fileSize) {
        this.fileStream = fileStream;
        this.bucketName = bucketName;
        this.filePath = filePath;
        this.fileLength = fileSize;
    }
}