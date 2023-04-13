package com.kosuri.rxkolan.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ambulance_price")
public class AmbulancePrice extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="price_id")
    protected String priceId;

    @Column(name="price_per_km",nullable = false)
    private BigDecimal pricePerKilometre;

    @Column(name="contact_number")
    private Long contactNumber;

    @Column(name="waiting_charges")
    private BigDecimal waitingCharges;


    @Column(name="update_src_Location",nullable = false,length = 45)
    private String updatedSrcLocation;


}
