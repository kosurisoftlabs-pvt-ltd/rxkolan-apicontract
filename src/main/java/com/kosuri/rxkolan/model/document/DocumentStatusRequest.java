package com.kosuri.rxkolan.model.document;

import com.kosuri.rxkolan.entity.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Data
public class DocumentStatusRequest {

    @NotNull(message = "Document Id Cannot Be Null")
    private String documentId;

    @NotNull(message = "Approval Status Needs to Be There")
    private ApprovalStatus approvalStatus;

    private String adminComment;
}