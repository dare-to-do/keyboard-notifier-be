package com.daretodo.keyboardnotifier.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    public User(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
