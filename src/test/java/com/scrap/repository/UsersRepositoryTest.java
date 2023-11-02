package com.scrap.repository;

import com.scrap.model.entity.User;
import com.scrap.util.MockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UsersRepositoryTest {

  @Autowired
  private UsersRepository usersRepository;

  @Test
  void testSaveUser() {
    User savedUser = usersRepository.save(MockData.GET_USER());

    User retrievedUser = usersRepository.findById(savedUser.getId()).orElse(null);

    assertNotNull(retrievedUser);
    assertEquals(savedUser.getId(), retrievedUser.getId());
    assertEquals(MockData.GET_USER().getUsername(), retrievedUser.getUsername());
  }
}