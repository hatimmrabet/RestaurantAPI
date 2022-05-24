package com.lapetitesyrienne.api.models;

public class CommandeItem {

    private Article article;
    private int quantity;

    public CommandeItem() {
    }

    public CommandeItem(Article article, int quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CommandeItem [article=" + article + ", quantity=" + quantity + "]";
    }
    
}
