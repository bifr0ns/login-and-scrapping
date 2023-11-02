package com.scrap.util.component;

import com.scrap.config.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class KeyLoader {
  private final RSAKeyProperties rsaKeyProperties;

  @Autowired
  public KeyLoader(RSAKeyProperties rsaKeyProperties) {
    this.rsaKeyProperties = rsaKeyProperties;
  }

  public RSAPrivateKey loadPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String privateKeyString = rsaKeyProperties.getPrivateKey();

    String privateKeyPem = privateKeyString
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll("\\R", "")
            .replace("-----END PRIVATE KEY-----", "");

    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    return (RSAPrivateKey) privateKey;
  }

  public RSAPublicKey loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String publicKeyString = rsaKeyProperties.getPublicKey();

    String publicKeyPem = publicKeyString
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replaceAll("\\R", "")
            .replace("-----END PUBLIC KEY-----", "");

    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPem);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    return (RSAPublicKey) publicKey;
  }
}
