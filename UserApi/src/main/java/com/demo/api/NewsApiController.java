package com.demo.api;

import com.demo.model.Article;
import com.demo.model.ArticlePage;
import com.demo.model.Category;
import com.demo.model.CategoryPage;
import com.demo.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/api/news/*")
public class NewsApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final transient NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String[] parts = (req.getPathInfo() == null ? "" : req.getPathInfo()).split("/");
        String resource = parts.length > 1 ? parts[1] : "";
        Integer id = parts.length == 3 ? parseId(parts[2]) : null;

        try {
            if (parts.length == 2 && "home".equals(resource)) {
                JSONArray result = new JSONArray();
                for (Category c : newsService.getHomeCategories()) {
                    result.put(category(c).put("articles", articles(c.getArticles())));
                }
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(result.toString());
                return;
            }
            if ("categories".equals(resource) && id != null) {
                CategoryPage page = newsService.getCategoryPage(id);
                if (page != null) {
                    JSONArray subs = new JSONArray();
                    for (Category s : page.getSubCategories()) subs.put(category(s));
                    JsonIO.write(resp, HttpServletResponse.SC_OK, new JSONObject()
                            .put("category", category(page.getCategory()))
                            .put("parent", page.getParent() == null ? JSONObject.NULL : category(page.getParent()))
                            .put("subCategories", subs)
                            .put("articles", articles(page.getArticles())));
                    return;
                }
            }
            if ("articles".equals(resource) && id != null) {
                ArticlePage page = newsService.getArticlePage(id);
                if (page != null) {
                    Article a = page.getArticle();
                    JsonIO.write(resp, HttpServletResponse.SC_OK, new JSONObject()
                            .put("article", article(a).put("content", a.getContent()))
                            .put("category", category(page.getCategory())));
                    return;
                }
            }
        } catch (SQLException e) {
            throw new ServletException("News API failed", e);
        }
        JsonIO.error(resp, HttpServletResponse.SC_NOT_FOUND, "Not found");  
    }

  
    private static JSONObject category(Category c) {
        return new JSONObject().put("id", c.getId()).put("name", c.getName());
    }

    private static JSONObject article(Article a) {
        return new JSONObject().put("id", a.getId()).put("title", a.getTitle()).put("summary", a.getSummary());
    }

    private static JSONArray articles(List<Article> list) {
        JSONArray result = new JSONArray();
        for (Article a : list) result.put(article(a));
        return result;
    }

    private static Integer parseId(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}