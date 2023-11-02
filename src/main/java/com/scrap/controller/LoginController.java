package com.scrap.controller;

import com.scrap.model.request.RequestLogin;
import com.scrap.model.request.RequestSignup;
import com.scrap.model.response.ResponseApi;
import com.scrap.service.IJwtService;
import com.scrap.service.ILoginService;
import com.scrap.util.Constants;
import com.scrap.util.Urls;
import com.scrap.util.component.PasswordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for login purposes
 */
@RestController
@RequestMapping("")
public class LoginController {

  @Autowired
  private ILoginService loginService;

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private PasswordHandler passwordHandler;

  /**
   * Get JWToken from a login
   *
   * @param requestLogin Username, email and password
   * @return User JWToken
   */
  @CrossOrigin(origins = Urls.ORIGEN)
  @PostMapping("/login")
  public ResponseEntity<ResponseApi<String>> login(@RequestBody RequestLogin requestLogin) {

    String hashedPass = loginService.getToken(requestLogin);

    boolean verified = passwordHandler.verifyPassword(requestLogin.getPassword(), hashedPass);

    if (!verified) {
      return createNewResponseApi(Constants.BAD_USER_OR_PASS, Constants.BAD_REQUEST, HttpStatus.UNAUTHORIZED);
    }

    String token = jwtService.createJWT(requestLogin.getEmail());

    return createNewResponseApi(token, Constants.SUCCESS, HttpStatus.OK);
  }

  /**
   * Get Token from a login
   *
   * @param requestSignup Email and password
   * @return JWToken
   */
  @CrossOrigin(Urls.ORIGEN)
  @PostMapping("/signup")
  public ResponseEntity<ResponseApi<Object>> getSignup(@RequestBody RequestSignup requestSignup) {

    String hashedPass = passwordHandler.hashPassword(requestSignup.getPassword());
    requestSignup.setPassword(hashedPass);

    loginService.signup(requestSignup);

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }

  private ResponseEntity<ResponseApi<String>> createNewResponseApi(
          String response, String msgResponse, HttpStatus httpStatus
  ) {
    ResponseApi<String> responseBody = ResponseApi.<String>builder()
            .msgResponse(msgResponse)
            .response(response)
            .build();

    return new ResponseEntity<>(
            responseBody, httpStatus
    );
  }

}
