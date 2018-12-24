package com.example.user1801.onlinemotel.firebaseThing;

public class JavaBeanKey {
    String chaosKey;
    String macAddress;

    public JavaBeanKey() {
        super();
    }

    public JavaBeanKey(String chaosKey, String macAddress) {
        this.chaosKey = chaosKey;
        this.macAddress = macAddress;
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
