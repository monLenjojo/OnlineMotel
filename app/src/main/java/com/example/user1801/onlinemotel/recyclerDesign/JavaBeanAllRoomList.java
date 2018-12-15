package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanAllRoomList {
    String where;
    String roomNmae;
    int money;
    int people;

    public JavaBeanAllRoomList() {
        super();
    }

    public JavaBeanAllRoomList(String where, String roomNmae, int money, int people) {
        this.where = where;
        this.roomNmae = roomNmae;
        this.money = money;
        this.people = people;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}
