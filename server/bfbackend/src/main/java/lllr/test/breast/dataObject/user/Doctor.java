package lllr.test.breast.dataObject.user;

public class Doctor {
    private Integer id;

    private String userName;

    private String userPassword;

    private String token;

    private String name;

    private Integer consultCost;

    private String licenseNumber;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", consultCost=" + consultCost +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getConsultCost() {
        return consultCost;
    }

    public void setConsultCost(Integer consultCost) {
        this.consultCost = consultCost;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }
}