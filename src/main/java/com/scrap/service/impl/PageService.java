package com.scrap.service.impl;

import com.scrap.model.entity.Page;
import com.scrap.model.entity.PageUrl;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import com.scrap.repository.PageRepository;
import com.scrap.repository.PageUrlRepository;
import com.scrap.service.IPageService;
import com.scrap.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService implements IPageService {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private PageUrlRepository pageUrlRepository;

  @Override
  public void getLinksFromUrl(String url) {

  }

  @Override
  public List<ResponsePage> getPagesForUser(Integer userId) {
    List<Page> pages = pageRepository.findByUserId(userId);

    return pages.stream()
            .map(page -> {
              String totalLinks = page.getCount() != -1 ? String.valueOf(page.getCount()) : Constants.IN_PROGRESS;

              return ResponsePage.builder()
                      .name(page.getName())
                      .totalLinks(totalLinks)
                      .build();
            })
            .toList();
  }

  @Override
  public List<ResponsePageInfo> getPageInfo(Integer pageId) {
    List<PageUrl> pageInfo = pageUrlRepository.findByPageId(pageId);

    return pageInfo.stream()
            .map(info -> ResponsePageInfo.builder()
                    .name(info.getName())
                    .link(info.getUrl())
                    .build())
            .toList();
  }

  @Override
  public String getUserFromPageId(Integer pageId) {
    return pageRepository.findById(pageId)
            .map(page -> page.getUserId().toString())
            .orElse(null);
  }

}
