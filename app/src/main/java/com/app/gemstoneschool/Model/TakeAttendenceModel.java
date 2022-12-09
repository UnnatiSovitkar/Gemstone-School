package com.app.gemstoneschool.Model;

public class TakeAttendenceModel {
    private String classn,boardname,sec_name,start_year,today_att_status,class_id
            ,board_id,sec_id,ay_id;

    public TakeAttendenceModel(String classn, String boardname, String sec_name, String start_year,
                               String today_att_status, String class_id, String board_id, String sec_id, String ay_id) {
        this.classn = classn;
        this.boardname = boardname;
        this.sec_name = sec_name;
        this.start_year = start_year;
        this.today_att_status = today_att_status;
        this.class_id = class_id;
        this.board_id = board_id;
        this.sec_id = sec_id;
        this.ay_id = ay_id;
    }

    public String getClassn() {
        return classn;
    }

    public void setClassn(String classn) {
        this.classn = classn;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getToday_att_status() {
        return today_att_status;
    }

    public void setToday_att_status(String today_att_status) {
        this.today_att_status = today_att_status;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public String getAy_id() {
        return ay_id;
    }

    public void setAy_id(String ay_id) {
        this.ay_id = ay_id;
    }
}
