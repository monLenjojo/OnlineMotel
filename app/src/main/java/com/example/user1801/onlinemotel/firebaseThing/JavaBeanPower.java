package com.example.user1801.onlinemotel.firebaseThing;

public class JavaBeanPower {
    String current;
    String voltage;
    String totalPower;
    String room;

    public JavaBeanPower() {
    }

    public JavaBeanPower(String current, String voltage, String totalPower, String room) {
        this.current = current;
        this.voltage = voltage;
        this.totalPower = totalPower;
        this.room = room;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
