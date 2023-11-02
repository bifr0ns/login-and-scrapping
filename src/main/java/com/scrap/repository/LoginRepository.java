package com.scrap.repository;


import com.scrap.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {

  Login findByEmail(String email);
}
