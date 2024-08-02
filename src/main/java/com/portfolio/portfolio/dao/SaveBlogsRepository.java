package com.portfolio.portfolio.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portfolio.portfolio.Entities.AddBlogsEntity;

public interface SaveBlogsRepository extends JpaRepository<AddBlogsEntity, Integer> {

    @Query("select b from AddBlogsEntity b order by b.blogId desc")

    Page<AddBlogsEntity> findAllOrderByBlogsIdDesc(Pageable pageable);

       // find blogs  from id
      AddBlogsEntity findByblogId(int blogId);
    
}