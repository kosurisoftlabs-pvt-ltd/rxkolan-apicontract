package com.kosuri.rxkolan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "uploaded_document")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UploadedDocument extends BaseEntity {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="document_id")
    protected String documentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", length = 100)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", length = 100)
    private LocationType locationType;

    @NotNull
    @Column(name = "unique_storage_path", updatable = false, nullable = false)
    @JsonIgnore
    private String uniqueStoragePath;

    /**
     * Original name of the file
     */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "extension", nullable = false)
    private String type;

    @Column(name = "bucket")
    private String bucket;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 100)
    private ApprovalStatus approvalStatus = ApprovalStatus.APPROVED;

    @Column(name = "admin_comment", length = 1000)
    private String adminComment;

}