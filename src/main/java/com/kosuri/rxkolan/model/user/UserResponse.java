package com.kosuri.rxkolan.model.user;

import com.kosuri.rxkolan.entity.ServiceOfferedEnum;
import com.kosuri.rxkolan.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String email;

    private String userId;

    private String phoneNumber;

    private boolean emailVerified;

    private boolean phoneVerified;

    private ServiceOfferedEnum serviceOffered;


    public UserResponse(User user){
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.userId = user.getPhoneNumber();
        this.emailVerified = user.isEmailVerified();
        this.phoneVerified = user.isPhoneVerified();
        this.serviceOffered= ServiceOfferedEnum.get(user.getServiceOffer());
    }


}
