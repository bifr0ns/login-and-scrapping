package com.scrap.repository;


import com.scrap.model.entity.PageUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageUrlRepository extends JpaRepository<PageUrl, Integer> {

  Page<PageUrl> findByPageId(Integer id, Pageable pageable);
}
