package com.scrap.service;

import com.scrap.model.entity.Page;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;

import java.util.List;

public interface IPageService {

  Page createNewPage(Integer userId, String url);

  void getLinksFromUrl(Page page, String url);

  List<ResponsePage> getPagesForUser(Integer userId);

  List<ResponsePageInfo> getPageInfo(Integer pageId);

  String getUserFromPageId(Integer pageId);

}
