package com.example.hatayli.webappi.Models;

// https://reqres.in/api/users?page=1
// https://reqres.in/api/users?page=2

import java.io.Serializable;

/*
    Model
    ---------
    "id":7,
    "email":"michael.lawson@reqres.in",
    "first_name":"Michael",
    "last_name":"Lawson",
    "avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg"

 */
public class User implements Serializable {
    private String id,email,first_name,last_name,imageUrl;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

