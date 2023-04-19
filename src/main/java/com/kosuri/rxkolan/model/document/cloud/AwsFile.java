package com.kosuri.rxkolan.model.document.cloud;

import com.amazonaws.services.s3.model.S3Object;
import com.kosuri.rxkolan.model.document.CloudFile;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AwsFile implements CloudFile {
    private String bucketName;
    private S3Object cloudFile;
    private String url;
    private String fileName;
    private String publicUrl;
    private byte[] bytes;

    @Override
    public String getFileUrl() {
        return url;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFilePublicUrl() {
        return publicUrl;
    }

    @Override
    public byte[] getContentBytes() {
        return bytes;
    }
}