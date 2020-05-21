package com.example.ijobs.data;

import java.util.List;

public class JobSeekerCv {
    private String userId;
    private String pastExperience;
    private String pastJobs;
    private List<String> servicesOffered;
    private String skills;

    public JobSeekerCv(){
        
    }

    public JobSeekerCv(String userId, String pastExperience, String pastJobs, List<String> servicesOffered, String skills) {
        this.userId = userId;
        this.pastExperience = pastExperience;
        this.pastJobs = pastJobs;
        this.servicesOffered = servicesOffered;
        this.skills = skills;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPastExperience() {
        return pastExperience;
    }

    public void setPastExperience(String pastExperience) {
        this.pastExperience = pastExperience;
    }

    public String getPastJobs() {
        return pastJobs;
    }

    public void setPastJobs(String pastJobs) {
        this.pastJobs = pastJobs;
    }

    public List<String> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
