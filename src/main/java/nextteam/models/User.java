/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author vnitd
 */
public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private String avatarURL;
    private String firstname;
    private String lastname;
    private String studentCode;
    private String phoneNumber;
    private String major;
    private String academicYear;
    private String gender;
    private String dob;
    private String homeTown;
    private String facebookUrl;
    private String linkedInUrl;
    private Date createdAt;
    private Date updatedAt;

    public User() {
    }

    public User(String email, String username, String password, String studentCode, String phoneNumber, String gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.studentCode = studentCode;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public User(int id, String email, String username, String password, String avatarURL, String firstname, String lastname, String studentCode, String phoneNumber, String major, String academicYear, String gender, String dob, String homeTown, String facebookUrl, String linkedInUrl, Date createdAt, Date updatedAt) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatarURL = avatarURL;
        this.firstname = firstname;
        this.lastname = lastname;
        this.studentCode = studentCode;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.academicYear = academicYear;
        this.gender = gender;
        this.dob = dob;
        this.homeTown = homeTown;
        this.facebookUrl = facebookUrl;
        this.linkedInUrl = linkedInUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
