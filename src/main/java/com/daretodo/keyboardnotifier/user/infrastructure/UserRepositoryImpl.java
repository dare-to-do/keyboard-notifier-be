package com.daretodo.keyboardnotifier.user.infrastructure;

import com.daretodo.keyboardnotifier.user.domain.User;
import com.daretodo.keyboardnotifier.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return userEntity.toDomain();
    }
}
