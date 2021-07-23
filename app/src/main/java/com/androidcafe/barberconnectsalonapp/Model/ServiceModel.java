package com.androidcafe.barberconnectsalonapp.Model;

public class ServiceModel {
    public String name,price,haircut,shave,color,massage,threading;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getThreading() {
        return threading;
    }

    public void setThreading(String threading) {
        this.threading = threading;
    }

    public String getHaircut() {
        return haircut;
    }

    public void setHaircut(String haircut) {
        this.haircut = haircut;
    }

    public String getShave() {
        return shave;
    }

    public void setShave(String shave) {
        this.shave = shave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name+" "+price;
    }
}
