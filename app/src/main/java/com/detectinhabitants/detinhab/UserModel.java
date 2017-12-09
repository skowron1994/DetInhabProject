package com.detectinhabitants.detinhab;

public class UserModel {

    private int usrID;
    private String usrName;
    private String usrSurname;
    private String usrLogin;
    private String usrPassword;
    private String usrMail;
    private int usPermission;

    public int getUsrID() {
        return usrID;
    }

    public void setUsrID(int usrID) {
        this.usrID = usrID;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrSurname() {
        return usrSurname;
    }

    public void setUsrSurname(String usrSurname) {
        this.usrSurname = usrSurname;
    }

    public String getUsrLogin() {
        return usrLogin;
    }

    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrMail() {
        return usrMail;
    }

    public void setUsrMail(String usrMail) {
        this.usrMail = usrMail;
    }

    public int getUsPermission() {
        return usPermission;
    }

    public void setUsPermission(int usPermission) {
        this.usPermission = usPermission;
    }
}

