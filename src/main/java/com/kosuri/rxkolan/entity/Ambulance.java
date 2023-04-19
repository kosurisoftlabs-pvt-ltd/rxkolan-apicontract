package com.kosuri.rxkolan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ambulance")
@AttributeOverride(name = "id", column = @Column(name = "ambulance_reg_no"))
public class Ambulance extends BaseEntity implements Serializable {


    @Id
    @Column(name="ambulance_reg_no")
    protected String ambulanceRegNumber;

    @Column(name = "userId", length = 100)
    private String userEmail;

    @Column(name="phone_number",length = 50)
    private String phoneNumber;

    @Column(name="base_location",length = 300)
    private String baseLocation;

    @Column(name="vehicle_brand",length = 300)
    private String vehicleBrand;

    @Column(name="vehicle_model",length = 300)
    private String vehicleModel;

    @Column(name="rto_reg_location",length = 300)
    private String rtoRegisteredLocation;

    @Column(name="state",length = 45)
    private String state;

    @Column(name="vin",length = 45)
    private String vin;

    @Column(name="owner_name",length = 45)
    private String ownerName;


    @Column(name = "reg_date", columnDefinition = "DATE")
    private LocalDate registeredDate;

    @Column(name = "additional_features",length=100)
    private String additionalFeatures;

    @Column(name = "verified", columnDefinition = "INT default 1")
    private boolean verified=false;

    @Column(name = "verified_by",length = 45)
    private String verifiedBy;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "ambulance_doc_images", joinColumns = {
            @JoinColumn(name = "device_type_id", referencedColumnName = "ambulance_reg_no")}, inverseJoinColumns = {
            @JoinColumn(name = "document_id", referencedColumnName = "document_id", unique = true)})
    private List<UploadedDocument> ambulanceRelatedImages;
}
