package com.app.gemstoneschool.Model;

public class AttendenceModel {
    private String stud_att_id,inst_id,user_id,user_type,class_id,board_id,sec_id,ay_id,
            admission_id,att_status,leave_reason,stud_att_date,stud_att_status,created_at,
            updated_at;

    public AttendenceModel(String stud_att_id, String inst_id, String user_id, String user_type,
                           String class_id, String board_id, String sec_id, String ay_id,
                           String admission_id, String att_status, String leave_reason,
                           String stud_att_date, String stud_att_status, String created_at,
                           String updated_at) {
        this.stud_att_id = stud_att_id;
        this.inst_id = inst_id;
        this.user_id = user_id;
        this.user_type = user_type;
        this.class_id = class_id;
        this.board_id = board_id;
        this.sec_id = sec_id;
        this.ay_id = ay_id;
        this.admission_id = admission_id;
        this.att_status = att_status;
        this.leave_reason = leave_reason;
        this.stud_att_date = stud_att_date;
        this.stud_att_status = stud_att_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getStud_att_id() {
        return stud_att_id;
    }

    public void setStud_att_id(String stud_att_id) {
        this.stud_att_id = stud_att_id;
    }

    public String getInst_id() {
        return inst_id;
    }

    public void setInst_id(String inst_id) {
        this.inst_id = inst_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
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

    public String getAdmission_id() {
        return admission_id;
    }

    public void setAdmission_id(String admission_id) {
        this.admission_id = admission_id;
    }

    public String getAtt_status() {
        return att_status;
    }

    public void setAtt_status(String att_status) {
        this.att_status = att_status;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public String getStud_att_date() {
        return stud_att_date;
    }

    public void setStud_att_date(String stud_att_date) {
        this.stud_att_date = stud_att_date;
    }

    public String getStud_att_status() {
        return stud_att_status;
    }

    public void setStud_att_status(String stud_att_status) {
        this.stud_att_status = stud_att_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
