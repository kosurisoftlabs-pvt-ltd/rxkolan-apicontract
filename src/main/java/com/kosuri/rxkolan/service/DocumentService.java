package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.entity.ApprovalStatus;
import com.kosuri.rxkolan.entity.DocumentType;
import com.kosuri.rxkolan.entity.UploadedDocument;
import com.kosuri.rxkolan.model.document.DocumentDetails;
import com.kosuri.rxkolan.model.document.DocumentStatusRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    List<UploadedDocument> uploadDocuments(List<MultipartFile> documents, DocumentType documentType, String id, String path, ApprovalStatus approvalStatus);

    List<UploadedDocument> uploadDocuments(List<MultipartFile> documents, DocumentType documentType, String id, String path, ApprovalStatus approvalStatus, boolean isPublic);

    List<DocumentDetails> buildDocumentDetails(List<UploadedDocument> documentList, boolean isPublic);

    DocumentDetails updateDocumentStatus(DocumentStatusRequest documentStatusRequest);
}