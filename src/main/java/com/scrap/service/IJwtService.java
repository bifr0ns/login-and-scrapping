package com.scrap.service;

public interface IJwtService {

  /**
   * Create a new Json Web Token
   *
   * @param email Email of the user
   * @return a Json Web Token
   */
  String createJWT(String email);

  /**
   * Verify the jwt
   *
   * @param token  jwt
   * @param userId Id of the User
   */
  void verifyJWT(String token, String userId);

  /**
   * Get the User ID from the JWToken
   *
   * @param token JWT
   * @return User ID
   */
  String getUserIdByToken(String token);
}
