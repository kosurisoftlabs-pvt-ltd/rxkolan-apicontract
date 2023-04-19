package com.kosuri.rxkolan.service.impl;

import com.amazonaws.AmazonServiceException;
import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.exception.InternalException;
import com.kosuri.rxkolan.model.document.CloudFile;
import com.kosuri.rxkolan.model.document.cloud.GetAwsFileRequest;
import com.kosuri.rxkolan.model.document.cloud.GetAwsFileRequests;
import com.kosuri.rxkolan.model.document.cloud.PostAwsFileRequest;
import com.kosuri.rxkolan.repository.CloudDocumentRepository;
import com.kosuri.rxkolan.service.CloudService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AwsCloudServiceImpl implements CloudService {

    private final AppProperties appProperties;
    private final CloudDocumentRepository cloudDocumentRepository;

    @Override
    public String upload(MultipartFile file, String filePath) {
        return upload(file, filePath, false);
    }

    @Override
    public String upload(MultipartFile file, String filePath, boolean isPublic) {
        log.info("uploading file to cloud storage:{}", filePath);

        InputStream uploadedFileStream;

        try {
            uploadedFileStream = new BufferedInputStream(file.getInputStream());

        } catch (IOException ex) {
            log.error("Invalid or bad bulk request file with Error {}", ex.getMessage());

            throw new InternalException(HttpStatus.BAD_REQUEST,
                    "INVALID FILE", "Invalid File");
        }
        final String bucketName = appProperties.getAwsConfig().getPrivateBucketName();
        return uploadToAws(uploadedFileStream, file.getSize(), bucketName, filePath);
    }

    @Override
    public String getSignedUrl(String filePath) {
        var request = GetAwsFileRequest.builder().bucketName(appProperties.getAwsConfig().getPrivateBucketName()).filePath(filePath).build();
        try {
            return cloudDocumentRepository.getSignedUrl(request);
        } catch (AmazonServiceException ex) {
            log.error("file found for path:{}, aws storage error:{}", filePath, ex.getMessage());
            return null;
        }
    }

    @Override
    public String getPublicUrl(final String filePath) {
        try {
            return cloudDocumentRepository.getPublicUrl(appProperties.getAwsConfig().getPrivateBucketName(), filePath);
        } catch (AmazonServiceException ex) {
            log.error("file found for path:{}, aws storage error:{}", filePath, ex.getMessage());
            return null;
        }
    }

    @Override
    public String uploadFile(File file, String filePath) {
        log.info("uploading file to cloud storage:{}", filePath);
        InputStream uploadedFileStream;
        try {
            uploadedFileStream = new BufferedInputStream(new FileInputStream(file));

        } catch (IOException ex) {
            log.error("Invalid or bad bulk request file with error message {}", ex.getMessage());

            throw new InternalException(HttpStatus.BAD_REQUEST,
                    "INVALID FILE", "Invalid File");
        }
        return uploadToAws(uploadedFileStream, FileUtils.sizeOf(file), appProperties.getAwsConfig().getPrivateBucketName(), filePath);
    }

    @Override
    public CloudFile get(String filePath) {
        log.info("fetching file from aws:{}", filePath);
        var request = GetAwsFileRequest.builder().bucketName(appProperties.getAwsConfig().getPrivateBucketName()).filePath(filePath).build();

        var file = cloudDocumentRepository.get(request);

        log.info("Successfully fetched file from aws:{}", filePath);

        return file;
    }


    private String uploadToAws(InputStream inputStream, Long fileSize, String bucketName, String filePath) {
        try {
            var panUploadRequest = new PostAwsFileRequest(bucketName, filePath, inputStream, fileSize);
            return cloudDocumentRepository.save(panUploadRequest);
        } catch (AmazonServiceException ex) {
            log.error("Unable to upload file path:{} to s3 storage error:{}", filePath, ex.getMessage());
            throw new InternalException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                    "Unable to upload file to aws s3");
        }
    }

    @Override
    public void deleteFile(String filePath) {
        var deleteFileRequest = GetAwsFileRequest.builder().bucketName(appProperties.getAwsConfig().getPrivateBucketName()).filePath(filePath).build();
        cloudDocumentRepository.delete(deleteFileRequest);
    }

    @Override
    public void deleteFileInBatch(List<String> filePaths) {
        if (CollectionUtils.isNotEmpty(filePaths)) {
            cloudDocumentRepository.deleteInBatch(GetAwsFileRequests.builder().filePaths(filePaths).build());
        }
    }

}