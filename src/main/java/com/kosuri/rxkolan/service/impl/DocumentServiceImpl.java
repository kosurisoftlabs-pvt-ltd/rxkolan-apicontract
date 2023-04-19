package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.entity.ApprovalStatus;
import com.kosuri.rxkolan.entity.DocumentType;
import com.kosuri.rxkolan.entity.LocationType;
import com.kosuri.rxkolan.entity.UploadedDocument;
import com.kosuri.rxkolan.model.document.DocumentDetails;
import com.kosuri.rxkolan.model.document.DocumentStatusRequest;
import com.kosuri.rxkolan.repository.UploadedDocumentRepository;
import com.kosuri.rxkolan.service.CloudService;
import com.kosuri.rxkolan.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final CloudService cloudService;
    private final AppProperties appProperties;

    private final UploadedDocumentRepository uploadedDocumentRepository;

    @Override
    public List<UploadedDocument> uploadDocuments(List<MultipartFile> documents, DocumentType documentType, String id, String path, ApprovalStatus approvalStatus) {
        return uploadDocuments(documents, documentType, id, path, approvalStatus, false);
    }

    @Override
    public List<UploadedDocument> uploadDocuments(List<MultipartFile> documents, DocumentType documentType, String id, String path, ApprovalStatus approvalStatus, boolean isPublic) {
        List<UploadedDocument> uploadedDocuments = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(documents)) {
            documents.forEach(document -> {
                if (!document.isEmpty()) {
                    final String bucketName =  appProperties.getAwsConfig().getPrivateBucketName();
                    final String filePath = getFilePath(path, documentType, document, id);
                    cloudService.upload(document, filePath, isPublic);
                    uploadedDocuments.add(UploadedDocument.builder().documentType(documentType)
                            .locationType(LocationType.S3).uniqueStoragePath(filePath).fileName(document.getName())
                            .bucket(bucketName).approvalStatus(approvalStatus)
                            .type(FilenameUtils.getExtension(document.getOriginalFilename())).build());
                }
            });
        }
        return uploadedDocuments;
    }

    private String getFilePath(String path, DocumentType documentType, MultipartFile uploadedDocument, String id) {
        if (Objects.nonNull(uploadedDocument)) {
            return path + "/" + id + "/" + documentType.name() + "/" + uploadedDocument.getOriginalFilename();
        }
        return null;
    }

    @Override
    public List<DocumentDetails> buildDocumentDetails(List<UploadedDocument> documentList, boolean isPublic) {
        return documentList.stream().map(document -> {
            String url = isPublic ? cloudService.getPublicUrl(document.getUniqueStoragePath()) : cloudService.getSignedUrl(document.getUniqueStoragePath());
            return DocumentDetails.builder()
                    .documentPreviewUrl(url)
                    .documentId(document.getDocumentId()).documentType(document.getDocumentType())
                    .approvalStatus(document.getApprovalStatus())
                    .adminComment(document.getAdminComment()).build();
        }).toList();
    }

    @Override
    @Transactional
    public DocumentDetails updateDocumentStatus(DocumentStatusRequest documentStatusRequest) {
        Optional<UploadedDocument> documentOptional = uploadedDocumentRepository.findById(documentStatusRequest.getDocumentId());
        if (documentOptional.isPresent()) {
            UploadedDocument document = documentOptional.get();
            document.setApprovalStatus(documentStatusRequest.getApprovalStatus());
            document.setAdminComment(documentStatusRequest.getAdminComment());
            document = uploadedDocumentRepository.save(document);
            return DocumentDetails.builder()
                    .documentPreviewUrl(cloudService.getSignedUrl(document.getUniqueStoragePath()))
                    .documentId(document.getDocumentId()).documentType(document.getDocumentType())
                    .approvalStatus(document.getApprovalStatus())
                    .adminComment(document.getAdminComment()).build();
        } else {
            log.info("No Document Found with Document Id {}", documentStatusRequest.getDocumentId());
        }
        return DocumentDetails.builder().build();
    }
}