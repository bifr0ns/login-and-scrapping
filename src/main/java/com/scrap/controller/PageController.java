package com.scrap.controller;

import com.scrap.model.request.RequestPage;
import com.scrap.model.response.ResponseApi;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import com.scrap.service.IJwtService;
import com.scrap.service.IPageService;
import com.scrap.util.Constants;
import com.scrap.util.Response;
import com.scrap.util.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pages")
public class PageController {

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private IPageService pageService;

  @CrossOrigin(origins = Urls.ORIGEN)
  @PostMapping("")
  public ResponseEntity<ResponseApi<Object>> scrapPage(
          @RequestBody RequestPage requestPage,
          @RequestHeader String token
  ) {

    jwtService.verifyJWT(token, jwtService.getUserIdByToken(token));

    pageService.getLinksFromUrl(requestPage.getUrl());

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }

  @CrossOrigin(origins = Urls.ORIGEN)
  @GetMapping("")
  public ResponseEntity<ResponseApi<Object>> getPagesForUser(
          @RequestHeader String token
  ) {
    String userId = jwtService.getUserIdByToken(token);

    jwtService.verifyJWT(token, userId);

    List<ResponsePage> pagesForUser = pageService.getPagesForUser(Integer.valueOf(userId));

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .response(pagesForUser)
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }

  @CrossOrigin(origins = Urls.ORIGEN)
  @GetMapping("/{pageId}")
  public ResponseEntity<ResponseApi<Object>> getPageInfo(
          @PathVariable Integer pageId,
          @RequestHeader String token
  ) {
    String userIdByToken = jwtService.getUserIdByToken(token);

    jwtService.verifyJWT(token, userIdByToken);

    String userId = pageService.getUserFromPageId(pageId);

    if (!Objects.equals(userIdByToken, userId)) {
      return Response.createNewResponseApi(Constants.SESSION_ERROR,
              HttpStatus.UNAUTHORIZED.getReasonPhrase(),
              HttpStatus.UNAUTHORIZED);
    }

    List<ResponsePageInfo> linksForPage = pageService.getPageInfo(Integer.valueOf(userIdByToken));

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .response(linksForPage)
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }
}
