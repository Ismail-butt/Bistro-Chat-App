package com.IsmailUsman_i180516_i180634;

import java.io.Serializable;

public class Contact implements Serializable {

    private String Username, firstName, lastName, gender, bio;
    public String profilepic;
    private String email;
    private String password;
    private boolean online;

    public Contact() {
        this.Username = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.bio = "";
        this.profilepic = "";
        this.online = false;
    }

    public Contact(String Username, String firstName, String lastName, String gender, String bio, String profilepic) {
        this.Username = Username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bio = bio;
        this.profilepic = profilepic;
        this.online = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getprofilepic() {
        return profilepic;
    }

    public void setprofilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
