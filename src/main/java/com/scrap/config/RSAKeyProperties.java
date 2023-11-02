package com.scrap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rsa")
@Getter
@Setter
public class RSAKeyProperties {
  private String privateKey;
  private String publicKey;
}
