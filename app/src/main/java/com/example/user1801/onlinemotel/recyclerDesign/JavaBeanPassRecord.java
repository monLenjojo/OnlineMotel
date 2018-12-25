package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanPassRecord {
    String time;
    String name;

    public JavaBeanPassRecord() {
        super();
    }

    public JavaBeanPassRecord(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
