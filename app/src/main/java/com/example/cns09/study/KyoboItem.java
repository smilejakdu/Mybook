package com.example.cns09.study;

public class KyoboItem {
    private String img_url;
    private String title;
    private String author;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public KyoboItem(String img_url, String title, String author, String price) {
        this.img_url = img_url;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

