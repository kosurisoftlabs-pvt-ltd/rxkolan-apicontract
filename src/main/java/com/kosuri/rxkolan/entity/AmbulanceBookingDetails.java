package com.kosuri.rxkolan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "amb_booking_details")
@IdClass(BookingId.class)
public class AmbulanceBookingDetails extends BaseEntity{


    @Id
    @Column(name="booking_no",nullable = false,length = 45)
    private String bookingNumber;

    @Id
    @Column(name = "booking_date", columnDefinition = "DATE",nullable = false)
    private LocalDate bookingDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ambulance_reg_no")
    @JsonBackReference
    @ToString.Exclude
    private Ambulance ambulance;

    @Column(name="patient_name",nullable = false,length = 45)
    private String patientName;

    @Column(name="from_location",nullable = false,length = 45)
    private String fromLocation;

    @Column(name="to_location",nullable = false,length = 45)
    private String toLocation;

    @Column(name="customer_cont_num",length = 45)
    private String customerContactNumber;

    @Column(name="contact_person",length = 45)
    private String contactPerson;

    @Column(name="booked_By",length = 45)
    private String bookedBy;

    @Column(name="status",length = 45)
    private String status;

    @Column(name="remarks",length = 45)
    private String remarks;

}
