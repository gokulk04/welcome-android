package com.welcome.android.objects;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;
import com.welcome.android.utils.FirebaseDBUtils;

import java.util.List;

/**
 * Created by Kraji on 1/12/2017.
 */

public class User extends FirebaseObject{
    private String name;
    private String email;
    private String profPicUrl;
    private List<String> memberOrganizationIds;
    private List<String> adminOrganizationIds;
    private Long birthday;
    private String phoneNumber;
    private String race;
    private String gender;
    private String occupation;
    private String school;
    private String major;
    private String year;
    private String company;
    private String jobTitle;

    public User() {

    }

    @Exclude
    public static Task<User> getById(String id) {
        return new FirebaseDBUtils<User>(User.class).getById(id);
    }

    @Exclude
    public static Task<List<User>> getAll() {
        return new FirebaseDBUtils<User>(User.class).getAll();
    }

    @Exclude
    public static Task<List<User>> filterAllByParamEqualTo(String param, Object value) {
        return new FirebaseDBUtils<User>(User.class).filterAllByParamEqualTo(param, value);
    }

    @Exclude
    protected void copy(FirebaseObject o) {
        User other = null;
        try {
            other = (User) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("can't cast object to SignIn");
        }

        name = other.name;
        email = other.email;
        profPicUrl = other.profPicUrl;
        memberOrganizationIds = other.memberOrganizationIds;
        adminOrganizationIds = other.adminOrganizationIds;
        birthday = other.birthday;
        phoneNumber = other.phoneNumber;
        race = other.race;
        gender = other.gender;
        occupation = other.occupation;
        school = other.school;
        major = other.major;
        year = other.year;
        company = other.company;
        jobTitle = other.jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfPicUrl() {
        return profPicUrl;
    }

    public void setProfPicUrl(String profPicUrl) {
        this.profPicUrl = profPicUrl;
    }

    public List<String> getMemberOrganizationIds() {
        return memberOrganizationIds;
    }

    public void setMemberOrganizationIds(List<String> memberOrganizationIds) {
        this.memberOrganizationIds = memberOrganizationIds;
    }

    public List<String> getAdminOrganizationIds() {
        return adminOrganizationIds;
    }

    public void setAdminOrganizationIds(List<String> adminOrganizationIds) {
        this.adminOrganizationIds = adminOrganizationIds;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


}
