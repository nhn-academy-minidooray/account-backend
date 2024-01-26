package com.nhnacademy.minidooray.account.backend.domain.requestbody;

import lombok.Data;

@Data
public class AccountRegisterRequest {
    String id;
    String password;
    String email;
    String name;
}
