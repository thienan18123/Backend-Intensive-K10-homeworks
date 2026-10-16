package com.demo.model;

public class Article {
    private final int id;
    private final int categoryId;
    private final String title;
    private final String summary;
    private final String content;

    public Article(int id, int categoryId, String title, String summary, String content) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public int getId()         { return id; }
    public int getCategoryId() { return categoryId; }
    public String getTitle()   { return title; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
}