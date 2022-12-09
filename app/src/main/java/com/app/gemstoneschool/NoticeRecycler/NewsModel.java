package com.app.gemstoneschool.NoticeRecycler;

public class NewsModel {
    private String id,description,title,news_image,date;

    public NewsModel(String id, String description, String title, String news_image, String date) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.news_image = news_image;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
