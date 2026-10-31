package com.demo.service;

import com.demo.dao.SavedArticleDao;
import com.demo.model.Article;

import java.sql.SQLException;
import java.util.List;


public class SavedListService {
    private final SavedArticleDao savedDao = new SavedArticleDao();
    private final NewsService newsService = new NewsService();

    public List<Article> getSaved(int userId) throws SQLException {
        return savedDao.findByUser(userId);
    }

    public boolean isSaved(int userId, int articleId) throws SQLException {
        return savedDao.isSaved(userId, articleId);
    }

  
    public boolean save(int userId, int articleId) throws SQLException {
        if (newsService.getArticlePage(articleId) == null) {
            return false;
        }
        savedDao.save(userId, articleId);
        return true;
    }

    public void remove(int userId, int articleId) throws SQLException {
        savedDao.remove(userId, articleId);
    }
}