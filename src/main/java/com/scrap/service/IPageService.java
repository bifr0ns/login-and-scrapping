package com.scrap.service;

import com.scrap.model.entity.Url;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface IPageService {

  CompletableFuture<Url> createNewPageAsync(Integer userId, String url);

  CompletableFuture<Void> getLinksFromUrlAsync(Url page, String url);

  Page<ResponsePage> getPagesForUser(Integer userId, Pageable pageable);

  Page<ResponsePageInfo> getPageInfo(Integer pageId, Pageable pageable);

  String getUserFromPageId(Integer pageId);
}
