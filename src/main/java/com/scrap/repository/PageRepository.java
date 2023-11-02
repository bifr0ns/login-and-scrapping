package com.scrap.repository;


import com.scrap.model.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {
  List<Page> findByUserId(Integer id);

}
