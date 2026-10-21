package com.demo.model;

import java.util.List;


public class CategoryPage {
    private final Category category;
    private final Category parent;                 
    private final List<Category> subCategories;
    private final List<Article> articles;

    public CategoryPage(Category category, Category parent,
                        List<Category> subCategories, List<Article> articles) {
        this.category = category;
        this.parent = parent;
        this.subCategories = subCategories;
        this.articles = articles;
    }

    public Category getCategory()            { return category; }
    public Category getParent()              { return parent; }
    public List<Category> getSubCategories() { return subCategories; }
    public List<Article> getArticles()       { return articles; }
}