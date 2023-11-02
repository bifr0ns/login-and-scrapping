package com.scrap.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.scrap.model.entity.User;
import com.scrap.repository.LoginRepository;
import com.scrap.service.IJwtService;
import com.scrap.util.Constants;
import com.scrap.util.component.KeyLoader;
import com.scrap.util.exception.MyCustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;

@Service
public class JwtService implements IJwtService {

  @Autowired
  private KeyLoader keyLoader;

  @Autowired
  private LoginRepository loginRepository;

  @SneakyThrows
  @Override
  public String createJWT(String email) {
    String token;

    User user = loginRepository.findByEmail(email).getUser();

    try {
      Algorithm algorithm = Algorithm.RSA256(keyLoader.loadPublicKey(), keyLoader.loadPrivateKey());
      token = JWT.create()
              .withSubject(user.getId().toString())
              .withIssuedAt(Instant.now())
              .withExpiresAt(Instant.now().plusSeconds(Constants.WEEK))
              .sign(algorithm);
    } catch (JWTCreationException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new MyCustomException(e.getMessage(), e.getCause());
    }

    return token;
  }

  @SneakyThrows
  @Override
  public void verifyJWT(String token, String userId) {
    try {
      Algorithm algorithm = Algorithm.RSA256(keyLoader.loadPublicKey(), keyLoader.loadPrivateKey());
      JWTVerifier verifier = JWT.require(algorithm)
              .withSubject(userId)
              .build();

      verifier.verify(token);
    } catch (JWTVerificationException e) {
      throw new MyCustomException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public String getUserIdByToken(String token) {
    return JWT.decode(token).getSubject();
  }
}
