package lllr.test.breast.dataObject.user;

import java.util.Date;

public class User {
    private Integer userId;

    private Integer age;

    private String creditId;

    private Integer pregnantType;

    private String pregnantWeek;

    private String job;

    private Date confinementDate;

    private Integer confinementWeek;

    private Integer confinementType;

    private String userName;

    private String userPassword;

    private String userToken;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId == null ? null : creditId.trim();
    }

    public Integer getPregnantType() {
        return pregnantType;
    }

    public void setPregnantType(Integer pregnantType) {
        this.pregnantType = pregnantType;
    }

    public String getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(String pregnantWeek) {
        this.pregnantWeek = pregnantWeek == null ? null : pregnantWeek.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Date getConfinementDate() {
        return confinementDate;
    }

    public void setConfinementDate(Date confinementDate) {
        this.confinementDate = confinementDate;
    }

    public Integer getConfinementWeek() {
        return confinementWeek;
    }

    public void setConfinementWeek(Integer confinementWeek) {
        this.confinementWeek = confinementWeek;
    }

    public Integer getConfinementType() {
        return confinementType;
    }

    public void setConfinementType(Integer confinementType) {
        this.confinementType = confinementType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken == null ? null : userToken.trim();
    }
}