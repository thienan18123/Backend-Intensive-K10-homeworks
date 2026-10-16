package com.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final int id;
    private final String name;
    private final Integer parentId;                 
    private List<Article> articles = new ArrayList<>();

    public Category(int id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId()                 { return id; }
    public String getName()            { return name; }
    public Integer getParentId()       { return parentId; }
    public List<Article> getArticles() { return articles; }

    public void setArticles(List<Article> articles) { this.articles = articles; }
}