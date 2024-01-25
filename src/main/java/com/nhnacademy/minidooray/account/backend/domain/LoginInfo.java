package com.nhnacademy.minidooray.account.backend.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LoginInfo {
    String id;

    String password;
}
