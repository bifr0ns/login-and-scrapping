package com.scrap.service.impl;

import com.scrap.repository.LoginRepository;
import com.scrap.util.MockData;
import com.scrap.util.component.KeyLoader;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JwtServiceTest {
  @InjectMocks
  private JwtService jwtService;

  @Mock
  private LoginRepository loginRepository;
  @Mock
  private KeyLoader keyLoader;

  @Test
  void testGetUserByToken() {
    String user = jwtService.getUserIdByToken(MockData.JWT);

    assertEquals("3", user);
  }
}