
package com.codephillip.app.automatedirrigationsystem.jsonmodels.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("crop")
    @Expose
    private Integer crop;

    public User(int crop_id) {
        this.crop = crop_id;
    }

    public User(String name, String address, String phoneNumber, String password, int crop){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.crop = crop;
    }

    public User(int id, String name, String address, String phoneNumber, int crop){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.crop = crop;
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(Integer id, String name, String address, String phoneNumber, Integer crop) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.crop = crop;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCrop() {
        return crop;
    }

    public void setCrop(Integer crop) {
        this.crop = crop;
    }

}
