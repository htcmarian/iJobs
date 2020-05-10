package com.example.ijobs.viewmodels;

import java.util.Date;
import java.util.List;

public class JobSeekerProfileViewModel {
    private String name;
    private Date birthDate;
    private String address;
    private String postalCode;
    private String city;
    private String phoneNumber;
    private List<ServiceOfferListItemViewModel> servicesOffered;

    public JobSeekerProfileViewModel() {
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ServiceOfferListItemViewModel> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<ServiceOfferListItemViewModel> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
