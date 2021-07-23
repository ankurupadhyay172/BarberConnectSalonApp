package com.androidcafe.barberconnectsalonapp.Model;

public class HelpModel {

    String tvTitle,tvDescription;


    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }

    public HelpModel(String tvTitle, String tvDescription) {
        this.tvTitle = tvTitle;
        this.tvDescription = tvDescription;
    }
}
