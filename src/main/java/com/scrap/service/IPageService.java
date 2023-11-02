package com.scrap.service;

import com.scrap.model.entity.Page;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPageService {

  CompletableFuture<Page> createNewPageAsync(Integer userId, String url);

  CompletableFuture<Void> getLinksFromUrlAsync(Page page, String url);

  List<ResponsePage> getPagesForUser(Integer userId);

  List<ResponsePageInfo> getPageInfo(Integer pageId);

  String getUserFromPageId(Integer pageId);
}
