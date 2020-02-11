package com.example.zusha;

import java.util.Date;

public class Report {
    private String regNo;
    private String sacco;
    private Float speed;
    private Date currentDateandTime;
    private String location;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getSacco() {
        return sacco;
    }

    public void setSacco(String sacco) {
        this.sacco = sacco;
    }

    public String getLocation() {
        return sacco;
    }

    public void setLocation(String sacco) {
        this.sacco = sacco;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Date getCurrentDateandTime() {
        return currentDateandTime;
    }

    public void setCurrentDateandTime(Date currentDateandTime) {
        this.currentDateandTime = currentDateandTime;
    }

    public Report() {

    }
}
