package com.example.user1801.onlinemotel.recyclerDesign;
//"98:D3:31:FB:19:CE"
public class JavaBeanMyRoom {
    String checkIn;
    String checkOut;
    String name;
    String room;
    String roomKey;
    String chaosKey;
    String macAddress;

    public JavaBeanMyRoom() {
    }

    public JavaBeanMyRoom(String checkIn, String checkOut, String name, String room, String roomKey, String chaosKey, String macAddress) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.room = room;
        this.roomKey = roomKey;
        this.chaosKey = chaosKey;
        this.macAddress = macAddress;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
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

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getChaosKey() {
        return chaosKey;
    }

    public void setChaosKey(String chaosKey) {
        this.chaosKey = chaosKey;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}