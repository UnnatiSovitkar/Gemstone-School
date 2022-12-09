package com.app.gemstoneschool.Model;

public class PrevHwModel {

    private String phw_sub,phw_title,phw_desc,phw_status,hw_material_file,sr_no;

    public PrevHwModel(String sr_no, String phw_sub, String phw_title, String phw_desc, String phw_status,String hw_material_file) {
        this.sr_no = sr_no;
        this.phw_sub = phw_sub;
        this.phw_title = phw_title;
        this.phw_desc = phw_desc;
        this.phw_status = phw_status;
        this.hw_material_file=hw_material_file;
    }

    public String getHw_material_file() {
        return hw_material_file;
    }

    public void setHw_material_file(String hw_material_file) {
        this.hw_material_file = hw_material_file;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getPhw_sub() {
        return phw_sub;
    }

    public void setPhw_sub(String phw_sub) {
        this.phw_sub = phw_sub;
    }

    public String getPhw_title() {
        return phw_title;
    }

    public void setPhw_title(String phw_title) {
        this.phw_title = phw_title;
    }

    public String getPhw_desc() {
        return phw_desc;
    }

    public void setPhw_desc(String phw_desc) {
        this.phw_desc = phw_desc;
    }

    public String getPhw_status() {
        return phw_status;
    }

    public void setPhw_status(String phw_status) {
        this.phw_status = phw_status;
    }
}
