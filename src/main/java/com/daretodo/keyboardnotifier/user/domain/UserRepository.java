package com.daretodo.keyboardnotifier.user.domain;

import com.daretodo.keyboardnotifier.user.infrastructure.UserEntity;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    User save(User user);
}
