package com.scrap.service.impl;

import com.scrap.model.entity.Login;
import com.scrap.model.entity.User;
import com.scrap.repository.LoginRepository;
import com.scrap.repository.UsersRepository;
import com.scrap.util.MockData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceTest {

  @InjectMocks
  private LoginService loginService;

  @Mock
  private LoginRepository loginRepository;
  @Mock
  private UsersRepository usersRepository;

  @Test
  void testGetToken() {
    when(loginRepository.findByEmail(MockData.REQUESTLOGIN.getEmail())).thenReturn(MockData.GET_LOGIN());

    String token = loginService.getToken(MockData.REQUESTLOGIN);

    assertEquals(MockData.HASHED_PASS, token);
  }

  @Test
  void testSignup() {
    when(usersRepository.save(Mockito.any(User.class))).thenReturn(MockData.GET_USER());
    when(loginRepository.save(Mockito.any(Login.class))).thenReturn(MockData.GET_LOGIN());

    loginService.signup(MockData.REQUEST_SIGNUP);

    verify(loginRepository).save(any());
  }
}