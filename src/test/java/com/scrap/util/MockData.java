package com.scrap.util;

import com.scrap.model.entity.Login;
import com.scrap.model.entity.PageUrl;
import com.scrap.model.entity.Url;
import com.scrap.model.entity.User;
import com.scrap.model.request.RequestLogin;
import com.scrap.model.request.RequestSignup;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData {
  public static String TOKEN = "token";
  public static String JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjk4OTAxMTAxLCJleHAiOjE2OTk1MDU5MDF9.vEnUTzCXLGqggOYhoe4PkmCkLFYTlfm0rivXsVLBQlbfypUnLnq9dcSesRPwkFPgnjlRr6Hb8M2_vmcxAeTQ0MRVyEe18xFhGUpDls_oVc26dV_5IojADZ3ENk1621wiSzpEy1uYwpGUM96Pfx72TXSVgzfQOJ08JHhugQgmfkJRtw5B3287i8cGln4O0lCHiM4hTw9_3mza3631ADGXaRRG7fexm25NtVEwI190k93YibqEw-FRO5p4ccXjO2FTqYpguJ4zJKoX5kkv8azXq4YESEx5T82T4ezHMXImDa6v3y2W1F-lP5mAogrd-HfnyY2_zlt8RmNdFePRtsOMuw";
  public static String EMAIL = "test@gmail.com";
  public static String HASHED_PASS = "@@@";
  public static String URL = "https://wikipedia.com";
  public static Integer ID = 1;
  static List<ResponsePageInfo> itemsPageInfo = Arrays.asList(
          new ResponsePageInfo("Name1", "Link1"),
          new ResponsePageInfo("Name2", "Link2")
  );
  public static final Page<ResponsePageInfo> PAGE_INFO = new PageImpl<>(
          itemsPageInfo, Pageable.unpaged(), itemsPageInfo.size()
  );

  static List<ResponsePage> itemsPage = Arrays.asList(
          new ResponsePage(1, "Name1", "Link1"),
          new ResponsePage(2, "Name2", "Link2")
  );
  public static final Page<ResponsePage> PAGE = new PageImpl<>(
          itemsPage, Pageable.unpaged(), itemsPage.size()
  );

  public static final RequestLogin REQUESTLOGIN = new RequestLogin("test@example.com", "password");
  public static final RequestLogin REQUEST_LOGIN_FAILED = new RequestLogin("test@example.com", "bad_password");
  public static final RequestSignup REQUEST_SIGNUP = new RequestSignup("username", "test@example.com", "password");

  public static User GET_USER() {
    User user = new User();
    user.setUsername("username");
    user.setId(1);

    return user;
  }

  public static Login GET_LOGIN() {
    User user = GET_USER();
    Login login = new Login();
    login.setUser(user);
    login.setPassword(HASHED_PASS);
    return login;
  }

  public static Url GET_URL(){
    Url newUrl = new Url();
    newUrl.setUserId(1);
    newUrl.setId(1);
    newUrl.setCount(1);

    return newUrl;
  }

  public static Map<String, String> getSampleLinks() {
    Map<String, String> links = new HashMap<>();
    links.put("title", "Sample Title");
    links.put("link1", "Link 1");
    links.put("link2", "Link 2");
    return links;
  }

  public static List<Url> getSampleUrls() {
    Url url1 = new Url();
    url1.setId(1);
    url1.setName("Page 1");
    url1.setCount(10);
    url1.setCreatedAt(Timestamp.from(Instant.now()));
    url1.setUserId(1);

    Url url2 = new Url();
    url2.setId(2);
    url2.setName("Page 2");
    url2.setCount(5);
    url2.setCreatedAt(Timestamp.from(Instant.now()));
    url2.setUserId(1);

    return Arrays.asList(url1, url2);
  }

  public static List<PageUrl> getSamplePageUrls() {
    PageUrl pageUrl1 = new PageUrl();
    pageUrl1.setId(1);
    pageUrl1.setName("Link 1");
    pageUrl1.setUrl("https://example.com/link1");
    pageUrl1.setPage(getSampleUrls().get(0));

    PageUrl pageUrl2 = new PageUrl();
    pageUrl2.setId(1);
    pageUrl2.setName("Link 2");
    pageUrl2.setUrl("https://example.com/link2");
    pageUrl2.setPage(getSampleUrls().get(0));

    return Arrays.asList(pageUrl1, pageUrl2);
  }
}
