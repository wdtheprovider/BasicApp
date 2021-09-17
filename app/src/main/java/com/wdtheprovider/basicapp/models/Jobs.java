package com.wdtheprovider.basicapp.models;

public class Jobs {

    private String key;
    private  String job_title;
    private  String job_description;
    private  String job_experience;
    private  String job_location;
    private  String job_qualification;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Jobs() {
    }

    public Jobs(String job_title, String job_description, String job_experience, String job_location, String job_qualification) {
        this.job_title = job_title;
        this.job_description = job_description;
        this.job_experience = job_experience;
        this.job_location = job_location;
        this.job_qualification = job_qualification;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getJob_experience() {
        return job_experience;
    }

    public void setJob_experience(String job_experience) {
        this.job_experience = job_experience;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_qualification() {
        return job_qualification;
    }

    public void setJob_qualification(String job_qualification) {
        this.job_qualification = job_qualification;
    }
}
