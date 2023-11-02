package com.scrap.repository;

import com.scrap.model.entity.Url;
import com.scrap.model.entity.User;
import com.scrap.util.MockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PageRepositoryTest {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Test
  void testFindByUserId() {
    User user = usersRepository.save(MockData.GET_USER());

    Url url1 = pageRepository.save(MockData.getSampleUrls().get(0));

    Url url2 = pageRepository.save(MockData.getSampleUrls().get(1));

    Pageable pageable = PageRequest.of(0, 10);
    Page<Url> foundUrls = pageRepository.findByUserId(user.getId(), pageable);

    assertNotNull(foundUrls);
    assertEquals(2, foundUrls.getTotalElements());
    assertEquals(url1, foundUrls.getContent().get(0));
    assertEquals(url2, foundUrls.getContent().get(1));
  }
}