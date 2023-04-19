package com.kosuri.rxkolan.repository;

import com.kosuri.rxkolan.model.document.CloudFile;
import com.kosuri.rxkolan.model.document.cloud.GetCloudFileRequest;
import com.kosuri.rxkolan.model.document.cloud.GetCloudFileRequests;
import com.kosuri.rxkolan.model.document.cloud.PostCloudFileRequest;

public interface CloudDocumentRepository {

    String save(PostCloudFileRequest fileRequest);

    CloudFile get(GetCloudFileRequest fileRequest);

    String getSignedUrl(GetCloudFileRequest fileRequest);

    String getPublicUrl(String bucketName, String filePath);

    void delete(GetCloudFileRequest fileRequest);

    void deleteInBatch(GetCloudFileRequests fileRequests);
}