package com.kosuri.rxkolan.repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.model.document.CloudFile;
import com.kosuri.rxkolan.model.document.cloud.GetAwsFileRequest;
import com.kosuri.rxkolan.model.document.cloud.GetCloudFileRequest;
import com.kosuri.rxkolan.model.document.cloud.GetCloudFileRequests;
import com.kosuri.rxkolan.model.document.cloud.PostAwsFileRequest;
import com.kosuri.rxkolan.model.document.cloud.PostCloudFileRequest;
import com.kosuri.rxkolan.model.document.cloud.AwsFile;
import com.kosuri.rxkolan.model.document.cloud.GetAwsFileRequests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Repository implements CloudDocumentRepository {

    private final AppProperties appProperties;

    private final AmazonS3 amazonS3Client;

    @Override
    public String save(PostCloudFileRequest fileRequest) {
        if (fileRequest.getClass() == PostAwsFileRequest.class) {
            var uploadFile = (PostAwsFileRequest) fileRequest;
            if (uploadFile.getFileStream() != null && StringUtils.hasText(fileRequest.getFilePath())) {
                log.info("Uploading File:{} on Bucket:{}", uploadFile.getFilePath(), uploadFile.getBucketName());
                ObjectMetadata metadata = new ObjectMetadata();
                final PutObjectRequest putObjectRequest = new PutObjectRequest(uploadFile.getBucketName(), uploadFile.getFilePath(), uploadFile.getFileStream(), metadata);
                amazonS3Client.putObject(putObjectRequest);
                var url = getUrl(uploadFile);
                log.info("Successfully Uploaded File : {} on bucket {} with url:{}", uploadFile.getFilePath(), uploadFile.getBucketName(), url);
                return url;
            } else {
                log.error("Error in Saving File to AWS S3 Bucket ");
                throw new IllegalArgumentException("Error In Uploading File");
            }
        } else {
            throw new IllegalArgumentException("File should Be Compatible with AWS In Order to be uploaded to AWS");
        }
    }

    @Override
    public CloudFile get(GetCloudFileRequest fileRequest) {
        if (fileRequest.getClass() == GetAwsFileRequest.class) {
            S3Object s3Object = null;
            try {
                log.info("getting cloud file for file:{}", fileRequest.getFilePath());
                var awsRequest = (GetAwsFileRequest) fileRequest;
                var publicUrl = getSignedUrl(fileRequest);
                s3Object = amazonS3Client.getObject(awsRequest.getBucketName(), fileRequest.getFilePath());
                var url = amazonS3Client.getUrl(awsRequest.getBucketName(), fileRequest.getFilePath()).toExternalForm();

                var audioInputLength = (int) s3Object.getObjectMetadata().getContentLength();
                byte[] content = new byte[audioInputLength];
                var s3Stream = s3Object.getObjectContent();
                int read = s3Stream.read(content);
                if (read == -1) {
                    log.info("Read s3 content");
                }
                var downloadedFile = new AwsFile(awsRequest.getBucketName(), s3Object, url, fileRequest.getFilePath(), publicUrl, content);

                log.info("Successfully fetched file:{} with public url:{}", fileRequest.getFilePath(), publicUrl);
                log.debug(downloadedFile.toString());

                return downloadedFile;
            } catch (AmazonServiceException ex) {
                log.error("file not found for path:{}, aws storage error:{}", fileRequest.getFilePath(), ex.getMessage());

                return null;
            } catch (IOException ex) {
                log.error("error while reading file path:{}, io error:{}", fileRequest.getFilePath(), ex.getMessage());

                return null;
            } finally {
                try {
                    if (s3Object != null) {
                        s3Object.getObjectContent().abort();
                        s3Object.close();
                    }
                } catch (IOException e) {
                    log.error("Unable to close S3 object: {}", e.getMessage(), e);
                }
            }
        } else {
            throw new IllegalArgumentException("request should be compatible with AWS in order to download from AWS");
        }
    }

    @Override
    public String getSignedUrl(GetCloudFileRequest fileRequest) {
        var awsRequest = (GetAwsFileRequest) fileRequest;
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsRequest.getBucketName(), awsRequest.getFilePath());
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expirationTime);
        var url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public String getPublicUrl(final String bucketName, final String filePath) {
        return amazonS3Client.getUrl(bucketName, filePath).toExternalForm();
    }

    private String getUrl(PostAwsFileRequest fileRequest) {
        return amazonS3Client.getUrl(fileRequest.getBucketName(), fileRequest.getFilePath()).toExternalForm();
    }

    private Date getExpirationTime() {
        long currentExpirationTime = appProperties.getAwsConfig().getUrlExpirationTime();
        Date expiration = new Date();
        long milliSeconds = expiration.getTime() + 1000 * 60 * 60 * currentExpirationTime;
        expiration.setTime(milliSeconds);
        return expiration;
    }

    @Override
    public void delete(GetCloudFileRequest fileRequest) {
        var awsRequest = (GetAwsFileRequest) fileRequest;
        amazonS3Client.deleteObject(awsRequest.getBucketName(), awsRequest.getFilePath());
    }

    @Override
    public void deleteInBatch(GetCloudFileRequests fileRequests) {
        var awsRequest = (GetAwsFileRequests) fileRequests;
        List<DeleteObjectsRequest.KeyVersion> bulk = new ArrayList<>();
        for (int i = 0; i < awsRequest.getFilePaths().size(); i++) {
            bulk.add(new DeleteObjectsRequest.KeyVersion(awsRequest.getFilePaths().get(i)));
        }
        try {
            amazonS3Client.deleteObjects(new DeleteObjectsRequest(appProperties.getAwsConfig().getPrivateBucketName()).withKeys(bulk));
        } catch (Exception error) {
            log.error("Error in Deleting Object From AWS {}", error.getMessage());
        }
        bulk.clear();
    }
}