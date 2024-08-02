package com.portfolio.portfolio.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portfolio.portfolio.Entities.AddProjectEntity;

public interface addProjectsRepository extends JpaRepository<AddProjectEntity, Integer> {

    @Query("select p from AddProjectEntity p order by p.projectId desc")
    Page<AddProjectEntity> findAllOrderByProjectIdDesc(Pageable pageable);

    //Find  single project data from project  ID
    AddProjectEntity findByProjectId(int projectId);
    
}