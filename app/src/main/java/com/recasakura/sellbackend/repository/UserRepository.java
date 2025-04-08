package com.recasakura.sellbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recasakura.sellbackend.model.user.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmailAndPhone(String email, String phone);

    List<UserProjection> findAllBy();

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
