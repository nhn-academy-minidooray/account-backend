package com.nhnacademy.minidooray.account.backend.domain.requestbody;

import lombok.Data;

@Data
public class LoginInfoRequest {
    String id;
    String password;
}
