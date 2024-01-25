package com.nhnacademy.minidooray.account.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "Account")
@Builder
public class Account {
    @Id
    @Column(name = "account_id")
    private String id;

    @Column(name = "account_email")
    private String email;

    @Column(name = "account_password")
    private String password;

    @Column(name = "account_status")
    private String status;
}
