package com.scrap.util.component;

import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.stereotype.Component;

@Component
public class PasswordHandler {
  public String hashPassword(String plainPassword) {
    Hash hash = Password.hash(plainPassword)
            .addRandomSalt(12)
            .withArgon2();
    return hash.getResult();
  }

  public boolean verifyPassword(String plainPassword, String hashedPassword) {
    return Password.check(plainPassword, hashedPassword).withArgon2();
  }
}
