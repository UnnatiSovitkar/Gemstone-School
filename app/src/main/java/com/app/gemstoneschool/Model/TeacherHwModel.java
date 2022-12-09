package com.app.gemstoneschool.Model;

public class TeacherHwModel {
    private String id,year,boardn,clname,secname,subname, title,description,date,img;

    public TeacherHwModel(String id, String year, String boardn, String clname, String secname, String subname, String title, String description, String date, String img) {
        this.id = id;
        this.year = year;
        this.boardn = boardn;
        this.clname = clname;
        this.secname = secname;
        this.subname = subname;
        this.title = title;
        this.description = description;
        this.date = date;
        this.img = img;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBoardn() {
        return boardn;
    }

    public void setBoardn(String boardn) {
        this.boardn = boardn;
    }

    public String getClname() {
        return clname;
    }

    public void setClname(String clname) {
        this.clname = clname;
    }

    public String getSecname() {
        return secname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
