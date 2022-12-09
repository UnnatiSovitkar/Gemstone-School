package com.app.gemstoneschool.Model;

public class EqNewModel {
    private String id,name,stdname,mobile,emailid,pstd,msg,ptype,schname,year,date,regstatus;

    public EqNewModel(String id, String name, String stdname, String mobile, String emailid, String pstd, String msg, String ptype, String schname, String year, String date, String regstatus) {
        this.id = id;
        this.name = name;
        this.stdname = stdname;
        this.mobile = mobile;
        this.emailid = emailid;
        this.pstd = pstd;
        this.msg = msg;
        this.ptype = ptype;
        this.schname = schname;
        this.year = year;
        this.date = date;
        this.regstatus = regstatus;
    }

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

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPstd() {
        return pstd;
    }

    public void setPstd(String pstd) {
        this.pstd = pstd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getSchname() {
        return schname;
    }

    public void setSchname(String schname) {
        this.schname = schname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(String regstatus) {
        this.regstatus = regstatus;
    }
}
