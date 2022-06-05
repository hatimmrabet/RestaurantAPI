package com.lapetitesyrienne.api.models;

public class CommandeItem {

    private ArticleCommande article;
    private int quantity;

    public CommandeItem() {
    }

    public CommandeItem(ArticleCommande article, int quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public ArticleCommande getArticle() {
        return article;
    }

    public void setArticle(ArticleCommande article) {
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
