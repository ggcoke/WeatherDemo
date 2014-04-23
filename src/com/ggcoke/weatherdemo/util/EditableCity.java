package com.ggcoke.weatherdemo.util;

/**
 * Created by wanghuisong on 2014/4/23.
 */
public class EditableCity {
    private String cityName;
    private boolean editing;

    public EditableCity(String cityName, boolean editing) {
        this.cityName = cityName;
        this.editing = editing;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean isEditing() {
        return this.editing;
    }
}
