package com.nhnacademy.minidooray.account.backend.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AccountRegisterRequest {
    String id;
    String password;
    String email;
}
