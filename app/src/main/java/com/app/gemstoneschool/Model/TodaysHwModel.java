package com.app.gemstoneschool.Model;

public class TodaysHwModel {
    private int sr_no;
    private String thw_sub,thw_title,thw_desc,thw_status,thw_material_file;

    public TodaysHwModel(int sr_no, String thw_sub, String thw_title, String thw_desc, String thw_status, String thw_material_file) {
        this.sr_no = sr_no;
        this.thw_sub = thw_sub;
        this.thw_title = thw_title;
        this.thw_desc = thw_desc;
        this.thw_status = thw_status;
        this.thw_material_file = thw_material_file;
    }

    public int getSr_no() {
        return sr_no;
    }

    public void setSr_no(int sr_no) {
        this.sr_no = sr_no;
    }

    public String getThw_sub() {
        return thw_sub;
    }

    public void setThw_sub(String thw_sub) {
        this.thw_sub = thw_sub;
    }

    public String getThw_title() {
        return thw_title;
    }

    public void setThw_title(String thw_title) {
        this.thw_title = thw_title;
    }

    public String getThw_desc() {
        return thw_desc;
    }

    public void setThw_desc(String thw_desc) {
        this.thw_desc = thw_desc;
    }

    public String getThw_status() {
        return thw_status;
    }

    public void setThw_status(String thw_status) {
        this.thw_status = thw_status;
    }

    public String getThw_material_file() {
        return thw_material_file;
    }

    public void setThw_material_file(String thw_material_file) {
        this.thw_material_file = thw_material_file;
    }
}
