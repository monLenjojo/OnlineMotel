package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanAllRoomList {
    String address;
    String name;
    String money;
    String people;

    public JavaBeanAllRoomList() {
        super();
    }

    public JavaBeanAllRoomList(String address, String name, String money, String people) {
        this.address = address;
        this.name = name;
        this.money = money;
        this.people = people;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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