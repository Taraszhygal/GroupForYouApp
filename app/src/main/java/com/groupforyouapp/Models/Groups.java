package com.groupforyouapp.Models;

public class Groups {
    private String UserId;
    private Integer UserEXP;
    private String nameGroup;
    private String author;
    private String number;
    private String description;
    private String category;
    private String image;

    public Groups() {
    }

    public Groups(String nameGroup, String author, String number, String description, String category, String  image, String id, Integer exp) {
        this.nameGroup = nameGroup;
        this.author = author;
        this.number = number;
        this.description = description;
        this.category = category;
        this.image = image;
        this.UserId = id;
        this.UserEXP = exp;
    }

    public String getUId() {
        return UserId;
    }

    public void setUId(String id) {
        this.UserId = id;
    }

    public Integer getUserEXP() {
        return UserEXP;
    }

    public void setUserEXP(Integer userEXP) {
        this.UserEXP = userEXP;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
