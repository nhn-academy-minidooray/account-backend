package com.nhnacademy.minidooray.account.backend.domain.requestbody;

import lombok.Data;

@Data
public class AccountPageInfoRequest {
    String id;
    String email;
    String name;
}
