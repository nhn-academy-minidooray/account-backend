package com.nhnacademy.minidooray.account.backend.domain.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRegisterRequest {
    String id;
    String password;
    String email;
    String name;
}
