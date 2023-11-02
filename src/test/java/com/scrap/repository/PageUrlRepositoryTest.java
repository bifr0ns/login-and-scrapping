package com.scrap.repository;

import com.scrap.model.entity.PageUrl;
import com.scrap.model.entity.Url;
import com.scrap.util.MockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PageUrlRepositoryTest {

  @Autowired
  private PageUrlRepository pageUrlRepository;

  @Autowired
  private PageRepository pageRepository;

  @Test
  void testFindByPageId() {
    Url url = pageRepository.save(MockData.GET_URL());

    PageUrl pageUrl1 = pageUrlRepository.save(MockData.getSamplePageUrls().get(0));

    PageUrl pageUrl2 = pageUrlRepository.save(MockData.getSamplePageUrls().get(1));

    Pageable pageable = PageRequest.of(0, 10);
    Page<PageUrl> foundPageUrls = pageUrlRepository.findByPageId(url.getId(), pageable);

    assertNotNull(foundPageUrls);
    assertEquals(1, foundPageUrls.getTotalElements());
    assertTrue(foundPageUrls.getContent().contains(pageUrl1));
    assertTrue(foundPageUrls.getContent().contains(pageUrl2));
  }
}