package com.example.user1801.onlinemotel.chaosThing;

public class JavaBeanRoomKey {
    String chaosKey;
    String macAddress;
    String room;
    public JavaBeanRoomKey() {
    }

    public JavaBeanRoomKey(String chaosKey, String macAddress, String room) {
        this.chaosKey = chaosKey;
        this.macAddress = macAddress;
        this.room = room;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}