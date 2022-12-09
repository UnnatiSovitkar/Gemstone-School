package com.app.gemstoneschool.Model;

public class AttStdListModel {
    private boolean isSelected;
    private String sr_id,admi_id, stud_name,stud_phone,stud_email,attd_status,attd_reason;

    public AttStdListModel(String sr_id,String admi_id, String stud_name, String stud_phone,
                           String stud_email, String attd_status, String attd_reason) {
        this.sr_id = sr_id;
        this.admi_id=admi_id;
        this.stud_name = stud_name;
        this.stud_phone = stud_phone;
        this.stud_email = stud_email;
        this.attd_status = attd_status;
        this.attd_reason = attd_reason;
    }

    public AttStdListModel() {

    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_phone() {
        return stud_phone;
    }

    public void setStud_phone(String stud_phone) {
        this.stud_phone = stud_phone;
    }

    public String getStud_email() {
        return stud_email;
    }

    public void setStud_email(String stud_email) {
        this.stud_email = stud_email;
    }

    public String getAttd_status() {
        return attd_status;
    }

    public void setAttd_status(String attd_status) {
        this.attd_status = attd_status;
    }

    public String getAttd_reason() {
        return attd_reason;
    }

    public void setAttd_reason(String attd_reason) {
        this.attd_reason = attd_reason;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getAdmi_id() {
        return admi_id;
    }

    public void setAdmi_id(String admi_id) {
        this.admi_id = admi_id;
    }
}
