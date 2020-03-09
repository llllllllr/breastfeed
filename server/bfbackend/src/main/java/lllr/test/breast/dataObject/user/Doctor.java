package lllr.test.breast.dataObject.user;

public class Doctor {
    private Integer id;

    private String userName;

    private String userPassword;

    private String token;

    private String name;

    private String licenseNumber;

    private String expertIn;

    private Integer imagetextCost;

    private Integer voiceCost;

    private Integer videoCost;

    private String imgUrl;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    public String getExpertIn() {
        return expertIn;
    }

    public void setExpertIn(String expertIn) {
        this.expertIn = expertIn == null ? null : expertIn.trim();
    }

    public Integer getImagetextCost() {
        return imagetextCost;
    }

    public void setImagetextCost(Integer imagetextCost) {
        this.imagetextCost = imagetextCost;
    }

    public Integer getVoiceCost() {
        return voiceCost;
    }

    public void setVoiceCost(Integer voiceCost) {
        this.voiceCost = voiceCost;
    }

    public Integer getVideoCost() {
        return videoCost;
    }

    public void setVideoCost(Integer videoCost) {
        this.videoCost = videoCost;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", expertIn='" + expertIn + '\'' +
                ", imagetextCost=" + imagetextCost +
                ", voiceCost=" + voiceCost +
                ", videoCost=" + videoCost +
                ", imgUrl='" + imgUrl + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}