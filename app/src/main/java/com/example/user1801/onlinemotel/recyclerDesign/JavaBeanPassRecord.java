package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanPassRecord {
    String time;
    String name;
    String room;

    public JavaBeanPassRecord() {
        super();
    }

    public JavaBeanPassRecord(String time, String name, String room) {
        this.time = time;
        this.name = name;
        this.room = room;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
