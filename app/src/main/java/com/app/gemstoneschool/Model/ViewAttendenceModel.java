package com.app.gemstoneschool.Model;

public class ViewAttendenceModel {

    String srid,admiid,stud_name,att_status;

    public ViewAttendenceModel(String srid, String admiid, String stud_name, String att_status) {
        this.srid = srid;
        this.admiid = admiid;
        this.stud_name = stud_name;
        this.att_status = att_status;
    }

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    public String getAdmiid() {
        return admiid;
    }

    public void setAdmiid(String admiid) {
        this.admiid = admiid;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getAtt_status() {
        return att_status;
    }

    public void setAtt_status(String att_status) {
        this.att_status = att_status;
    }
}
