package com.scrap.repository;


import com.scrap.model.entity.PageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageUrlRepository extends JpaRepository<PageUrl, Integer> {

  List<PageUrl> findByPageId(Integer id);
}
