package com.kosuri.rxkolan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "amb_subscriber_detail_history")
public class AmbulanceSubscriberDetailHistory extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="subscriber_id")
    protected String subscriberId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ambulance_reg_no")
    @JsonBackReference
    @ToString.Exclude
    private Ambulance ambulance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id")
    @JsonBackReference
    @ToString.Exclude
    private MembershipPlan membershipPlan;

    @Column(name = "purchased_date", columnDefinition = "DATE")
    private LocalDate purchasedDate;

    @Column(name = "renewal_date", columnDefinition = "DATE")
    private LocalDate renewalDate;

    @Column(name = "expiry_date", columnDefinition = "DATE")
    private LocalDate expiryDate;

    @Column(name = "payment_method", length=45)
    private String paymentMethod;

    @Column(name = "pay_by", length=45)
    private String paidBy;

    @Column(name = "amount_paid", length=45)
    private String amountPaid;


    @Column(name = "purchase_id", length=45)
    private String purchaseId;

}
