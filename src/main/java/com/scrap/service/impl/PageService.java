package com.scrap.service.impl;

import com.scrap.model.entity.Page;
import com.scrap.model.entity.PageUrl;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import com.scrap.repository.PageRepository;
import com.scrap.repository.PageUrlRepository;
import com.scrap.service.IPageService;
import com.scrap.util.Constants;
import com.scrap.util.component.Scrapping;
import com.scrap.util.exception.MyCustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PageService implements IPageService {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private PageUrlRepository pageUrlRepository;

  @Autowired
  private Scrapping scrapping;
  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

  @Override
  public CompletableFuture<Page> createNewPageAsync(Integer userId, String url) {
    Page newPage = createNewPage(userId, url);
    return CompletableFuture.completedFuture(newPage);
  }

  @Override
  public CompletableFuture<Void> getLinksFromUrlAsync(Page newPage, String url) {
    CompletableFuture<Void> future = new CompletableFuture<>();

    executor.schedule(() -> {
      try {
        getLinksFromUrl(newPage, url);
        future.complete(null);
      } catch (Exception e) {
        future.completeExceptionally(e);
      }
    }, 0, TimeUnit.SECONDS);

    return future;
  }

  @Override
  public List<ResponsePage> getPagesForUser(Integer userId) {
    List<Page> pages = pageRepository.findByUserId(userId);

    return pages.stream()
            .map(page -> {
              String totalLinks = page.getCount() != -1 ? String.valueOf(page.getCount()) : Constants.IN_PROGRESS;

              return ResponsePage.builder()
                      .id(page.getId())
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

  private Page createNewPage(Integer userId, String url) {
    Page page = Page.builder()
            .name("Processing: " + url)
            .url(url)
            .userId(userId)
            .count(-1)
            .createdAt(Timestamp.from(Instant.now()))
            .build();

    return pageRepository.save(page);
  }

  @SneakyThrows
  private void getLinksFromUrl(Page page, String url) {
    Map<String, String> links;

    try {
      links = scrapping.getLinks(url);
    } catch (IOException e) {
      throw new MyCustomException(e.getMessage(), e.getCause());
    }

    List<PageUrl> pageUrls = new ArrayList<>();
    for (Map.Entry<String, String> entry : links.entrySet()) {
      String key = entry.getKey();

      if (key.equals("title")) {
        continue;
      }

      String value = entry.getValue().isEmpty() ? "No name found for link" : entry.getValue();

      PageUrl pageUrl = PageUrl.builder()
              .page(page)
              .name(value)
              .url(key)
              .build();

      pageUrls.add(pageUrl);
    }

    pageUrlRepository.saveAll(pageUrls);

    page.setName(links.get("title"));
    page.setCount(links.size() - 1);

    pageRepository.save(page);
  }

}
