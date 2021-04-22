package com.groupforyouapp.Models;


public class Users {
    private String name;
    private String s_name;
    private String pass;
    private String email;
    private String phone;
    private Integer exp;
    private Boolean isMentor;
    public  Users(){};

    public Users(String name, String s_name, String pass, String email, String phone, Boolean isMentor) {
        this.name = name;
        this.s_name = s_name;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.isMentor = isMentor;
    }


    public Users(String name, String s_name, String pass, String email, String phone, Boolean isMentor,Integer exp) {
        this.name = name;
        this.s_name = s_name;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.isMentor = isMentor;
        this.exp = exp ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getUserType() {
        return isMentor;
    }

    public void setUserType(Boolean isMentor) {
        this.isMentor = isMentor;
    }

    public Integer getExp() { return exp; }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
