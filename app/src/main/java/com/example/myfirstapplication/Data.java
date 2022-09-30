package com.example.myfirstapplication;

public class Data {
    private String name;
    private int price;
    private int index;

    public Data(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Data(String name, int price, int index) {
        this.name = name;
        this.price = price;
        this.index = index;
    }
}
