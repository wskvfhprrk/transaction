package com.example.jpa.repository;

import com.example.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * UserRepository
 *
 * 何哥 {@link  JpaRepository}
 * 2018/11/3 17:54
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
}
