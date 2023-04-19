package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.model.document.CloudFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.List;

public interface CloudService {

    String upload(MultipartFile file, String filePath);

    String upload(MultipartFile file, String filePath, boolean isPublic);

    String getSignedUrl(String filePath);

    String getPublicUrl(String filePath);

    String uploadFile(File file, String filePath);

    CloudFile get(String filePath);

    void deleteFile(String fileName);

    void deleteFileInBatch(List<String> fileName);


}