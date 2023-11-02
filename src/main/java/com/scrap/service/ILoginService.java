package com.scrap.service;

import com.scrap.model.request.RequestLogin;
import com.scrap.model.request.RequestSignup;

public interface ILoginService {
  /**
   * Get token from a login request
   *
   * @param request Info on the login
   * @return JWToken
   */
  String getToken(RequestLogin request);

  /**
   * Create a new user
   *
   * @param requestSignup information on the signup
   */
  void signup(RequestSignup requestSignup);
}
