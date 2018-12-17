package com.example.user1801.onlinemotel.recyclerDesign;
public class JavaBeanMyRoom{
    String checkIn;
    String stay;
    String where;
    String roomNmae;
    String money;
    String people;

    public JavaBeanMyRoom() {
    }

    public JavaBeanMyRoom(String checkIn, String stay, String where, String roomNmae, String money, String people) {
        this.checkIn = checkIn;
        this.stay = stay;
        this.where = where;
        this.roomNmae = roomNmae;
        this.money = money;
        this.people = people;
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

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getRoomNmae() {
        return roomNmae;
    }

    public void setRoomNmae(String roomNmae) {
        this.roomNmae = roomNmae;
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
