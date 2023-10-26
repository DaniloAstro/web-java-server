package com.example.webjavaserver;

import java.util.Objects;

import java.util.List;


public class User {
    public Integer id;
    public String name;
    public String email;
    public List<String> friends;

    public User(Integer id, String name, String email, List<String> friends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.friends = friends;
    }

    public User(){
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<String> getFriends(){
        return friends;
    }
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return
                false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) && Objects.equals(email,
                user.email) && Objects.equals(friends, user.friends);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, friends);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", friends=" + friends +
                '}';
    }
}