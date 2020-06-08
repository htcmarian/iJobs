package com.example.ijobs.viewmodels;

import java.util.Date;

public class JobPostViewModel {
    private String id;
    private String city;
    private String serviceRequired;
    private String jobType;
    private String jobDescription;
    private String skillsRequired;
    private String createdBy;
    private Date createdDate = new Date();

    public JobPostViewModel() {

    }

    public JobPostViewModel(String city, String serviceRequired, String jobType, String jobDescription, String skillsRequired) {
        this.city = city;
        this.serviceRequired = serviceRequired;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.skillsRequired = skillsRequired;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getServiceRequired() {
        return serviceRequired;
    }

    public void setServiceRequired(String serviceRequired) {
        this.serviceRequired = serviceRequired;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public void setCreatedBy(String createdByUserId) {
        this.createdBy = createdByUserId;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getServiceRequiredImageThumbnail() {
        if (getServiceRequired().contains(",")) {
            return "multiple";
        }

        return getServiceRequired().toLowerCase().replace(" ", "_");
    }
}
