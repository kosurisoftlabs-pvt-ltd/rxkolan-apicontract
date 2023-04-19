package com.kosuri.rxkolan.model.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kosuri.rxkolan.entity.ApprovalStatus;
import com.kosuri.rxkolan.entity.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DocumentDetails {

    private String documentPreviewUrl;
    private String documentId;
    private DocumentType documentType;

    private ApprovalStatus approvalStatus;

    private String adminComment;
    private boolean deleted;

}
