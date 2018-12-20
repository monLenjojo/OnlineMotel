package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanAllRoomList {
    String where;
    String roomName;
    String money;
    String people;

    public JavaBeanAllRoomList() {
        super();
    }

    public JavaBeanAllRoomList(String where, String roomName, String money, String people) {
        this.where = where;
        this.roomName = roomName;
        this.money = money;
        this.people = people;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
