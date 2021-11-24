package com.example.student;

public class Model {

    String id;
    String name;
    String role;
    String image;
    String height;
    String weight;
    String country;
    String comment;
    String format;

    public Model(String id, String name, String role, String image, String height, String weight, String country) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.country = country;
    }

    public Model(String image, String comment,String format) {
        this.comment = comment;
        this.format = format;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.comment = format;
    }

}
