package com.app.gemstoneschool.Model;

public class StdViewHwModel {
    String hwattid,hwid,hw_att_cdate,get_hw_info,hw_att_img;

    public StdViewHwModel(String hwattid, String hwid, String hw_att_cdate, String get_hw_info, String hw_att_img) {
        this.hwattid = hwattid;
        this.hwid = hwid;
        this.hw_att_cdate = hw_att_cdate;
        this.get_hw_info = get_hw_info;
        this.hw_att_img = hw_att_img;
    }

    public String getHwattid() {
        return hwattid;
    }

    public void setHwattid(String hwattid) {
        this.hwattid = hwattid;
    }

    public String getHwid() {
        return hwid;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }

    public String getHw_att_cdate() {
        return hw_att_cdate;
    }

    public void setHw_att_cdate(String hw_att_cdate) {
        this.hw_att_cdate = hw_att_cdate;
    }

    public String getGet_hw_info() {
        return get_hw_info;
    }

    public void setGet_hw_info(String get_hw_info) {
        this.get_hw_info = get_hw_info;
    }

    public String getHw_att_img() {
        return hw_att_img;
    }

    public void setHw_att_img(String hw_att_img) {
        this.hw_att_img = hw_att_img;
    }
}
