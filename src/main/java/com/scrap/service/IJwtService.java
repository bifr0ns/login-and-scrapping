package com.scrap.service;

public interface IJwtService {

  /**
   * Create a new Json Web Token
   *
   * @param email Email of the user
   * @return a Json Web Token
   */
  String createJWT(String email);
}
