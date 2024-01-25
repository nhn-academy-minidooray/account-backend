package com.nhnacademy.minidooray.account.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "Account")
public class Account {
    @Id
    @Column(name = "account_id")
    private String id;

    @Column(name = "account_email")
    private String email;

    @Column(name = "account_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private Status status;

    @Getter
    public enum Status {
        JOIN("가입"),
        DORMANT("휴면"),
        WITHDRAWAL("탈퇴");

        private final String value;

        Status(String value){
            this.value = value;
        }

        public static Status fromValue(String value) {
            for (Status status : Status.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }
}
