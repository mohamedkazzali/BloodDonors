package com.blooddonors.tntj.blooddonors;

/**
 * Created by itara on 17-04-2018.
 */

public class Donors {

    String fullName;
    String bloodGroup;
    String mobileNumber;
    String country;
    String state;
    String city;
    String local;

    public Donors() {}

    public Donors(String fullName, String bloodGroup, String mobileNumber, String country, String state, String city, String local) {
        this.fullName = fullName;
        this.bloodGroup = bloodGroup;
        this.mobileNumber = mobileNumber;
        this.country = country;
        this.state = state;
        this.city = city;
        this.local = local;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }


}
