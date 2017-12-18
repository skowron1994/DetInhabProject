package com.detectinhabitants.detinhab;


public class HabitantModel {
    private String habID;
    private String habName;
    private String habSurname;
    private int habAge;
    private int roomNumber;
    private int habStatus;
    private String habConsuelor;
    private int consContact;
    private String maxReturnTime;
    private String lastGuest;
    private int adnotations;

    public String getHabName() {
        return habName;
    }

    public void setHabName(String habName) {
        this.habName = habName;
    }

    public String getHabSurname() {
        return habSurname;
    }

    public void setHabSurname(String habSurname) {
        this.habSurname = habSurname;
    }

    public int getHabAge() {
        return habAge;
    }

    public void setHabAge(int habAge) {
        this.habAge = habAge;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getHabConsuelor() {
        return habConsuelor;
    }

    public void setHabConsuelor(String habConsuelor) {
        this.habConsuelor = habConsuelor;
    }

    public int getConsContact() {
        return consContact;
    }

    public void setConsContact(int consContact) {
        this.consContact = consContact;
    }

    public String getMaxReturnTime() {
        return maxReturnTime;
    }

    public void setMaxReturnTime(String maxReturnTime) {
        this.maxReturnTime = maxReturnTime;
    }

    public String getLastGuest() {
        return lastGuest;
    }

    public void setLastGuest(String lastGuest) {
        this.lastGuest = lastGuest;
    }

    public int getHabStatus() {
        return habStatus;
    }

    public void setHabStatus(int habStatus) {
        this.habStatus = habStatus;
    }

    public int getAdnotations() {
        return adnotations;
    }

    public void setAdnotations(int adnotations) {
        this.adnotations = adnotations;
    }

    public String getHabID() {
        return habID;
    }

    public void setHabID(String habID) {
        this.habID = habID;
    }
}
