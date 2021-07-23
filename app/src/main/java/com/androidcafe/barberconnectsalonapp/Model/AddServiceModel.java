package com.androidcafe.barberconnectsalonapp.Model;

public class AddServiceModel {

    int image;
    String name,service;

    public AddServiceModel(int image, String name, String service) {
        this.image = image;
        this.name = name;
        this.service = service;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
