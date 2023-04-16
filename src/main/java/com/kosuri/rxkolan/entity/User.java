package com.kosuri.rxkolan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_registration")
public class User implements UserDetails  {

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


    @Column(name = "account_non_expired", columnDefinition = "INT default 0")
    private boolean accountNonExpired;
    @Column(name = "credentials_non_expired", columnDefinition = "INT default 0")
    private boolean credentialsNonExpired;
    @Column(name = "account_non_locked", columnDefinition = "INT default 0")
    private boolean accountNonLocked;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "phone_number")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream().map(e -> new SimpleGrantedAuthority(e.getName())).toList();
    }

    @Override
    public String getUsername() {
        if(StringUtils.isAllEmpty(phoneNumber)){
            return email;
        }else return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
