package com.scrap.service.impl;

import com.scrap.model.entity.Login;
import com.scrap.model.entity.User;
import com.scrap.model.request.RequestLogin;
import com.scrap.model.request.RequestSignup;
import com.scrap.repository.LoginRepository;
import com.scrap.repository.UsersRepository;
import com.scrap.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService implements ILoginService {

  @Autowired
  private LoginRepository loginRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public String getToken(RequestLogin request) {
    Login login = loginRepository.findByEmail(request.getEmail());

    return login.getPassword();
  }

  @Override
  @Transactional
  public void signup(RequestSignup requestSignup) {
    User user = User.builder()
            .username(requestSignup.getUsername())
            .build();

    Login login = Login.builder()
            .user(usersRepository.save(user))
            .email(requestSignup.getEmail())
            .password(requestSignup.getPassword())
            .build();

    loginRepository.save(login);
  }

}
