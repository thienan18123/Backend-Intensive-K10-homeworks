package com.demo.ms_news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.ms_news.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
    @EntityGraph(attributePaths = "category")
    List<News> findAllByOrderByPublishedAtDesc();
}