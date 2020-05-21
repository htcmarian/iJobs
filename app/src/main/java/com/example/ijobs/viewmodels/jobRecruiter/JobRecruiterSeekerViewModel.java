package com.example.ijobs.viewmodels.jobRecruiter;

import java.util.List;

public class JobRecruiterSeekerViewModel {
    private  String userId;
    private  String name;
    private  String city;
    private  List<String> servicesOffered;

    public JobRecruiterSeekerViewModel(){

    }

    public JobRecruiterSeekerViewModel(String userId, String name, String city, List<String> servicesOffered)
    {
        this.userId = userId;
        this.name = name;
        this.city = city;
        this.servicesOffered = servicesOffered;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }
}
