package com.demo.service;

import com.demo.dao.ArticleDao;
import com.demo.dao.CategoryDao;
import com.demo.db.DbConnectionManager;
import com.demo.model.Article;
import com.demo.model.ArticlePage;
import com.demo.model.Category;
import com.demo.model.CategoryPage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsService {
    private final CategoryDao categoryDao = new CategoryDao();
    private final ArticleDao articleDao = new ArticleDao();

   
    public List<Category> getHomeCategories() throws SQLException {
        try (Connection c = DbConnectionManager.getConnection()) {         
            List<Category> roots = categoryDao.findRoots(c);               
            List<Article> articles = articleDao.findForRootCategories(c);   

            Map<Integer, List<Article>> byCategory = new HashMap<>();
            for (Article a : articles) {
                byCategory.computeIfAbsent(a.getCategoryId(), k -> new ArrayList<>()).add(a);
            }
            for (Category category : roots) {
                category.setArticles(byCategory.getOrDefault(category.getId(), new ArrayList<>()));
            }
            return roots;
        }                                                                    
    }
   
    public CategoryPage getCategoryPage(int id) throws SQLException {
        try (Connection c = DbConnectionManager.getConnection()) {
            Category category = categoryDao.findById(c, id);                    
            if (category == null) {
                return null;                                                   
            }
            Category parent = (category.getParentId() == null) ? null
                    : categoryDao.findById(c, category.getParentId());          
            List<Category> subCategories = categoryDao.findChildren(c, id);     
            List<Article> articles = articleDao.findByCategory(c, id);          
            return new CategoryPage(category, parent, subCategories, articles);
        }
    }

   
    public ArticlePage getArticlePage(int id) throws SQLException {
        try (Connection c = DbConnectionManager.getConnection()) {
            Article article = articleDao.findById(c, id);                       
            if (article == null) {
                return null;
            }
            Category category = categoryDao.findById(c, article.getCategoryId());
            if (category == null) {
                return null;                                                    
            }
            return new ArticlePage(article, category);
        }
    }
}