package com.app.gemstoneschool.Model;

public class SelectPType {

    String type,type_display;

    public SelectPType(String type, String type_display) {
        this.type = type;
        this.type_display = type_display;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_display() {
        return type_display;
    }

    public void setType_display(String type_display) {
        this.type_display = type_display;
    }

    @Override
    public String toString() {
        return type_display;
    }
}
