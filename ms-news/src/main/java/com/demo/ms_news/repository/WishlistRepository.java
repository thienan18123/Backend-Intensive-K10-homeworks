package com.demo.ms_news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.ms_news.entity.WishlistItem;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    @Query("select w from WishlistItem w join fetch w.news n join fetch n.category where w.userId = :userId")
    List<WishlistItem> findByUser(Long userId);
}