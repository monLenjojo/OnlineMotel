package com.example.user1801.onlinemotel.firebaseThing;

public class JavaBeanTime {
    String checkIn;
    String stay;

    public JavaBeanTime() {
        super();
    }

    public JavaBeanTime(String checkIn, String stay) {
        this.checkIn = checkIn;
        this.stay = stay;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }
}