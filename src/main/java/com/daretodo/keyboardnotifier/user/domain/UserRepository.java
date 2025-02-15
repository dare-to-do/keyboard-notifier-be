package com.daretodo.keyboardnotifier.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);

    User findById(Long id);
}
