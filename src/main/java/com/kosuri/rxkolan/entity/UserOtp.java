package com.kosuri.rxkolan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_otp")
public class UserOtp extends BaseEntity{


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="user_otp_id")
    protected String id;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "phone_number", nullable = false)
    private User user;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "email_otp", length = 20)
    private String emailOtp;

    @Column(name = "phone_otp", length = 20)
    private  String phoneOtp;

    @Column(name = "email_otp_date", columnDefinition = "DATE")
    private LocalDate emailOtpDate;

    @Column(name = "phone_otp_date", columnDefinition = "DATE")
    private LocalDate phoneOtpDate;


}
