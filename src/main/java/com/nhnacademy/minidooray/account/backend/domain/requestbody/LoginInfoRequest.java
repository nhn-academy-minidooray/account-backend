package com.nhnacademy.minidooray.account.backend.domain.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInfoRequest {
    String id;
    String password;
}
