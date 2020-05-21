package com.example.ijobs.viewmodels;

import java.util.List;

public class JobSeekerProfileViewModel {
    private String userId;
    private List<String> servicesOffered;
    private String pastExperience;
    private String pastJobs;
    private String skills;

    public JobSeekerProfileViewModel(String userId, List<String> servicesOffered, String pastExperience, String pastJobs, String skills) {

        this.userId = userId;
        this.servicesOffered = servicesOffered;
        this.pastExperience = pastExperience;
        this.pastJobs = pastJobs;
        this.skills = skills;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public List<String> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<String> servicesOffered){
        this.servicesOffered = servicesOffered;
    }

    public String getPastExperience() {
        return pastExperience;
    }

    public void setPastExperience(String pastExperience){
        this.pastExperience = pastExperience;
    }

    public String getPastJobs() {
        return pastJobs;
    }

    public void setPastJobs(String pastJobs){
        this.pastJobs = pastJobs;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills){
        this.skills = skills;
    }
}
