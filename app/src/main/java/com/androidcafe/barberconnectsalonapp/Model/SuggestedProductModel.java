package com.androidcafe.barberconnectsalonapp.Model;

public class SuggestedProductModel {

    Integer ivProduct;
    String tvProductname,tvPrice,service;

    public SuggestedProductModel(Integer ivProduct, String tvProductname, String tvPrice, String service) {
        this.ivProduct = ivProduct;
        this.tvProductname = tvProductname;
        this.tvPrice = tvPrice;
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getIvProduct() {
        return ivProduct;
    }

    public void setIvProduct(Integer ivProduct) {
        this.ivProduct = ivProduct;
    }

    public String getTvProductname() {
        return tvProductname;
    }

    public void setTvProductname(String tvProductname) {
        this.tvProductname = tvProductname;
    }

    public String getTvPrice() {
        return tvPrice;
    }

    public void setTvPrice(String tvPrice) {
        this.tvPrice = tvPrice;
    }


}
