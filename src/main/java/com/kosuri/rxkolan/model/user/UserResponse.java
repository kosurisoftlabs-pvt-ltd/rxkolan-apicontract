package com.kosuri.rxkolan.model.user;

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


}
