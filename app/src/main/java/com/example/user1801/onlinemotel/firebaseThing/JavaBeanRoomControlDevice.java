package com.example.user1801.onlinemotel.firebaseThing;

public class JavaBeanRoomControlDevice {
    String room;
    boolean fanState;
    boolean lampState;
    boolean lightState;
    boolean chaosLockState;
    boolean lockOpenState;
    boolean bluetoothConnectState;
    boolean buzzerOnState;

    public JavaBeanRoomControlDevice() {
    }

    public JavaBeanRoomControlDevice(String room, boolean fanState, boolean lampState, boolean lightState, boolean chaosLockState, boolean lockOpenState, boolean bluetoothConnectState, boolean buzzerOnState) {
        this.room = room;
        this.fanState = fanState;
        this.lampState = lampState;
        this.lightState = lightState;
        this.chaosLockState = chaosLockState;
        this.lockOpenState = lockOpenState;
        this.bluetoothConnectState = bluetoothConnectState;
        this.buzzerOnState = buzzerOnState;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isFanState() {
        return fanState;
    }

    public void setFanState(boolean fanState) {
        this.fanState = fanState;
    }

    public boolean isLampState() {
        return lampState;
    }

    public void setLampState(boolean lampState) {
        this.lampState = lampState;
    }

    public boolean isLightState() {
        return lightState;
    }

    public void setLightState(boolean lightState) {
        this.lightState = lightState;
    }

    public boolean isChaosLockState() {
        return chaosLockState;
    }

    public void setChaosLockState(boolean chaosLockState) {
        this.chaosLockState = chaosLockState;
    }

    public boolean isLockOpenState() {
        return lockOpenState;
    }

    public void setLockOpenState(boolean lockOpenState) {
        this.lockOpenState = lockOpenState;
    }

    public boolean isBluetoothConnectState() {
        return bluetoothConnectState;
    }

    public void setBluetoothConnectState(boolean bluetoothConnectState) {
        this.bluetoothConnectState = bluetoothConnectState;
    }

    public boolean isBuzzerOnState() {
        return buzzerOnState;
    }

    public void setBuzzerOnState(boolean buzzerOnState) {
        this.buzzerOnState = buzzerOnState;
    }
}
