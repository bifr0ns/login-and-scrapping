package com.scrap.repository;


import com.scrap.model.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Url, Integer> {
  Page<Url> findByUserId(Integer id, Pageable pageable);

}
