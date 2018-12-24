package com.example.user1801.onlinemotel.recyclerDesign;

public class JavaBeanRoomPath {
    String address;
    String name;
    String money;
    String people;
    String path;

    public JavaBeanRoomPath() {
        super();
    }

    public JavaBeanRoomPath(String address, String name, String money, String people, String path) {
        this.address = address;
        this.name = name;
        this.money = money;
        this.people = people;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
