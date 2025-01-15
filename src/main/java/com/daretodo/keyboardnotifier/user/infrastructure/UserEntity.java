package com.daretodo.keyboardnotifier.user.infrastructure;

import com.daretodo.keyboardnotifier.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.name = user.getName();
        userEntity.email = user.getEmail();
        userEntity.phoneNumber = user.getPhoneNumber();
        return userEntity;
    }

    public User toDomain() {
        return new User(id, name, email, phoneNumber);
    }
}
