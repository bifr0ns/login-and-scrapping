package com.scrap.controller;

import com.scrap.model.entity.Url;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/pages")
public class PageController {

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private IPageService pageService;

  @CrossOrigin(origins = Urls.ORIGEN)
  @PostMapping("")
  @Transactional
  public ResponseEntity<ResponseApi<Object>> scrapPage(
          @RequestBody RequestPage requestPage,
          @RequestHeader String token
  ) {
    String userIdByToken = jwtService.getUserIdByToken(token);
    jwtService.verifyJWT(token, userIdByToken);

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .build();

    CompletableFuture<Url> newPageFuture = pageService.createNewPageAsync(
            Integer.valueOf(userIdByToken), requestPage.getUrl()
    );

    pageService.getLinksFromUrlAsync(newPageFuture.join(), requestPage.getUrl());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @CrossOrigin(origins = Urls.ORIGEN)
  @GetMapping("")
  public ResponseEntity<ResponseApi<Object>> getPagesForUser(
          @RequestHeader String token,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    String userId = jwtService.getUserIdByToken(token);

    jwtService.verifyJWT(token, userId);

    Pageable pageable = PageRequest.of(page, size);

    Page<ResponsePage> pageInfo = pageService.getPagesForUser(Integer.valueOf(userId), pageable);

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .response(pageInfo.getContent())
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }

  @CrossOrigin(origins = Urls.ORIGEN)
  @GetMapping("/{pageId}")
  public ResponseEntity<ResponseApi<Object>> getPageInfo(
          @PathVariable Integer pageId,
          @RequestHeader String token,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    String userIdByToken = jwtService.getUserIdByToken(token);

    jwtService.verifyJWT(token, userIdByToken);

    String userId = pageService.getUserFromPageId(pageId);

    if (!Objects.equals(userIdByToken, userId)) {
      return Response.createNewResponseApi(Constants.R_NOT_FOUND,
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              HttpStatus.NOT_FOUND);
    }

    Pageable pageable = PageRequest.of(page, size);

    Page<ResponsePageInfo> pageInfo = pageService.getPageInfo(pageId, pageable);

    ResponseApi<Object> response = ResponseApi.builder()
            .msgResponse(Constants.SUCCESS)
            .response(pageInfo.getContent())
            .build();

    return new ResponseEntity<>(
            response, HttpStatus.OK
    );
  }
}
