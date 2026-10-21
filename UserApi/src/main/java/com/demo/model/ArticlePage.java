package com.demo.model;


public class ArticlePage {
    private final Article article;
    private final Category category;

    public ArticlePage(Article article, Category category) {
        this.article = article;
        this.category = category;
    }

    public Article getArticle()   { return article; }
    public Category getCategory() { return category; }
}