package com.androidcafe.barberconnectsalonapp.Model;

public class SalonModel {

    String id,name,image,address,openhours,phone,city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    double rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenhours() {
        return openhours;
    }

    public void setOpenhours(String openhours) {
        this.openhours = openhours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
