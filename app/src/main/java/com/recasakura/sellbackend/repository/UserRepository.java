package com.recasakura.sellbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recasakura.sellbackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
