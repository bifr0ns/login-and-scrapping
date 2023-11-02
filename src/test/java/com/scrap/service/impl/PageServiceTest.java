package com.scrap.service.impl;

import com.scrap.model.entity.PageUrl;
import com.scrap.model.entity.Url;
import com.scrap.model.response.ResponsePage;
import com.scrap.model.response.ResponsePageInfo;
import com.scrap.repository.PageRepository;
import com.scrap.repository.PageUrlRepository;
import com.scrap.util.MockData;
import com.scrap.util.component.Scrapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PageServiceTest {

  @InjectMocks
  private PageService pageService;

  @Mock
  private PageRepository pageRepository;
  @Mock
  private PageUrlRepository pageUrlRepository;
  @Mock
  private Scrapping scrapping;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateNewPageAsync() {
    Url newUrl = MockData.GET_URL();

    when(pageRepository.save(Mockito.any(Url.class))).thenReturn(newUrl);

    CompletableFuture<Url> future = pageService.createNewPageAsync(MockData.GET_USER().getId(), MockData.URL);

    assertTrue(future.isDone());
    assertEquals(newUrl, future.join());
  }

  @Test
  void testGetLinksFromUrlAsync() throws IOException {
    when(scrapping.getLinks(Mockito.anyString())).thenReturn(MockData.getSampleLinks());

    CompletableFuture<Void> future = pageService.getLinksFromUrlAsync(MockData.GET_URL(), MockData.URL);

    assertFalse(future.isDone());
    assertNull(future.join());
  }

  @Test
  void testGetPagesForUser() {
    Page<Url> page = new PageImpl<>(MockData.getSampleUrls());

    when(pageRepository.findByUserId(MockData.GET_USER().getId(),
            PageRequest.of(0, 10))).thenReturn(page);

    Page<ResponsePage> responsePage = pageService.getPagesForUser(MockData.GET_USER().getId(),
            PageRequest.of(0, 10));

    assertEquals(2, responsePage.getTotalElements());
  }

  @Test
  void testGetPageInfo() {
    Page<PageUrl> pageInfo = new PageImpl<>(MockData.getSamplePageUrls());

    when(pageUrlRepository.findByPageId(MockData.ID, PageRequest.of(0, 10))).thenReturn(pageInfo);

    Page<ResponsePageInfo> responsePageInfo = pageService.getPageInfo(MockData.ID, PageRequest.of(0, 10));

    assertEquals(2, responsePageInfo.getTotalElements());
  }

  @Test
  void testGetUserFromPageId() {
    when(pageRepository.findById(MockData.ID)).thenReturn(Optional.of(MockData.GET_URL()));

    String userId = pageService.getUserFromPageId(MockData.ID);

    assertEquals("1", userId);
  }

  @Test
  void testGetUserFromPageIdNotFound() {
    when(pageRepository.findById(MockData.ID)).thenReturn(Optional.empty());

    String userId = pageService.getUserFromPageId(MockData.ID);

    assertNull(userId);
  }

  @Test
  void testGetUserFromPageIdException() {
    when(pageRepository.findById(MockData.ID)).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(RuntimeException.class, () -> pageService.getUserFromPageId(MockData.ID));
  }

  @Test
  void testGetLinksFromUrlAsyncException() throws IOException {
    when(scrapping.getLinks(Mockito.anyString())).thenThrow(new IOException("Test Exception"));

    CompletableFuture<Void> future = pageService.getLinksFromUrlAsync(MockData.GET_URL(), "https://example.com");
    assertThrows(CompletionException.class, future::join);
  }
}