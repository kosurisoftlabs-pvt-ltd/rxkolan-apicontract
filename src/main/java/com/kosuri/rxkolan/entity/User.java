package com.kosuri.rxkolan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_reg")
public class User extends BaseEntity{

    @Id
    @Column(name="phone_number",length = 45)
    private String phoneNumber;

    @Column(name="email",length = 45,nullable = false)
    private String email;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "enabled", columnDefinition = "INT default 0")
    private boolean enabled;

    @Column(name = "email_verified", columnDefinition = "INT default 0")
    private boolean emailVerified;

    @Column(name = "phone_verified", columnDefinition = "INT default 0")
    private boolean phoneVerified;

    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "district_location", length = 100)
    private String districtLocation;

    @Column(name = "service_offer", length = 100)
    private String serviceOffer;


    @Column(name = "speciality", length = 100)
    private String speciality;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "phone_number")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}
