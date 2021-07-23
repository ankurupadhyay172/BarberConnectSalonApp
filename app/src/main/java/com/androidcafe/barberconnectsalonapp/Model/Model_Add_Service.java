package com.androidcafe.barberconnectsalonapp.Model;

import android.content.Context;

public class Model_Add_Service {

    String name,price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model_Add_Service(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
