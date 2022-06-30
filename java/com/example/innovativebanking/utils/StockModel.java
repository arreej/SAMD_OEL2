package com.example.innovativebanking.utils;

public class StockModel {
    private String stockSymbol;
    private int initialPrice;
    private int currentPrice;

    public StockModel(String stockSymbol, int initialPrice, int currentPrice) {
        this.stockSymbol = stockSymbol;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }
}
