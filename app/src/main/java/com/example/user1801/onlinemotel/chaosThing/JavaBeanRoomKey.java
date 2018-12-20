package com.example.user1801.onlinemotel.chaosThing;

public class JavaBeanRoomKey {
    String key;
    String macAddress;

    public JavaBeanRoomKey() {
    }

    public JavaBeanRoomKey(String key, String macAddress) {
        this.key = key;
        this.macAddress = macAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
