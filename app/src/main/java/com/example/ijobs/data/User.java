package com.example.ijobs.data;

public class User {
    private String email;
    private String userId;
    private JobSeekerCv jobSeekerCv;

    public User(){

    }

    public User(String userId, String email) {

        this.userId = userId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JobSeekerCv getJobSeekerCv() {
        return jobSeekerCv;
    }

    public void setJobSeekerCv(JobSeekerCv jobSeekerCv) {
        this.jobSeekerCv = jobSeekerCv;
    }
}
