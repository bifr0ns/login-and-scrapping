package com.scrap.controller;

import com.scrap.model.response.ResponseApi;
import com.scrap.service.impl.JwtService;
import com.scrap.service.impl.LoginService;
import com.scrap.util.Constants;
import com.scrap.util.MockData;
import com.scrap.util.component.PasswordHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LoginControllerTest {

  @InjectMocks
  private LoginController loginController;

  @Mock
  private JwtService jwtService;

  @Mock
  private LoginService loginService;

  @Mock
  private PasswordHandler passwordHandler;

  @Test
  void testSuccessfulLogin() {
    when(loginService.getToken(MockData.REQUESTLOGIN)).thenReturn(MockData.HASHED_PASS);
    when(passwordHandler.verifyPassword(MockData.REQUESTLOGIN.getPassword(), MockData.HASHED_PASS))
            .thenReturn(true);
    when(jwtService.createJWT(MockData.REQUESTLOGIN.getEmail())).thenReturn(MockData.TOKEN);

    ResponseEntity<ResponseApi<Object>> response = loginController.login(MockData.REQUESTLOGIN);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(MockData.TOKEN, response.getBody().getResponse());
    assertEquals(Constants.SUCCESS, response.getBody().getMsgResponse());
  }

  @Test
  void testFailedLogin() {

    when(loginService.getToken(MockData.REQUEST_LOGIN_FAILED)).thenReturn(MockData.HASHED_PASS);
    when(passwordHandler.verifyPassword(MockData.REQUEST_LOGIN_FAILED.getPassword(), MockData.HASHED_PASS))
            .thenReturn(false);

    ResponseEntity<ResponseApi<Object>> response = loginController.login(MockData.REQUEST_LOGIN_FAILED);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals(Constants.BAD_REQUEST, response.getBody().getMsgResponse());
    assertEquals(Constants.BAD_USER_OR_PASS, response.getBody().getResponse());
  }

  @Test
  void testSuccessfulSignup() {
    when(passwordHandler.hashPassword(MockData.REQUEST_SIGNUP.getPassword()))
            .thenReturn(MockData.HASHED_PASS);

    ResponseEntity<ResponseApi<Object>> response = loginController.getSignup(MockData.REQUEST_SIGNUP);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.SUCCESS, response.getBody().getMsgResponse());
  }
}
