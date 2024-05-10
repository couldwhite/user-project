package com.example.userservice.repository;

import com.example.userservice.entity.PgUser;
import com.example.userservice.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PgUserRepository extends JpaRepository<PgUser, Long> {
    PgUser getPgUserById(Long id);

}
