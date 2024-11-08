package com.dibyajyoti.cloudstorage.models;

public class User {

    private String userId;
    private int totalCapacity;
    private int usedCapacity;

    public User(String userId, int totalCapacity) {
        this.userId = userId;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = 0;
    }

    public String getUserId() {
        return userId;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public int getRemainingCapacity() {
        return totalCapacity - usedCapacity;
    }

    public void addUsedCapacity(int size) {
        this.usedCapacity += size;
    }

    public void subtractUsedCapacity(int size) {
        this.usedCapacity -= size;
    }
}
