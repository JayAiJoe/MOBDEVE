package com.mobdeve.group11.assist;

public class UITemplate {
    private String title, subtitle, notes;
    private Integer type;

    public UITemplate(String title, String subtitle, String notes) {
        this.title = title;
        this.subtitle = subtitle;
        this.notes = notes;
        this.type = 2;
    }
    public UITemplate() {
        this.title = "title";
        this.subtitle = "subtitle";
        this.notes = "notes";
        this.type = 1;
    }

    public String getTitle() { return title;}
    public String getSubject() { return subtitle;}
    public String getNotes() { return notes;}

    public void setTitle(String title){ this.title = title;}
    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}
}
