package com.scrap.repository;

import com.scrap.model.entity.Login;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class LoginRepositoryTest {

  @Autowired
  private LoginRepository loginRepository;

  @Test
  void testFindByEmail() {
    Login login = new Login();
    login.setEmail("test@example.com");
    login.setPassword("password");
    loginRepository.save(login);

    Login foundLogin = loginRepository.findByEmail("test@example.com");

    assertNotNull(foundLogin);
    assertEquals("test@example.com", foundLogin.getEmail());
    assertEquals("password", foundLogin.getPassword());
  }
}