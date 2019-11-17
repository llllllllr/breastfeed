package lllr.test.breast.dataObject.user;

public class Administrator {
    private Integer administratorId;

    private String administratorName;

    private String administratorPassword;

    private Integer administratorRight;

    private String administratorToken;

    public Integer getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName == null ? null : administratorName.trim();
    }

    public String getAdministratorPassword() {
        return administratorPassword;
    }

    public void setAdministratorPassword(String administratorPassword) {
        this.administratorPassword = administratorPassword == null ? null : administratorPassword.trim();
    }

    public Integer getAdministratorRight() {
        return administratorRight;
    }

    public void setAdministratorRight(Integer administratorRight) {
        this.administratorRight = administratorRight;
    }

    public String getAdministratorToken() {
        return administratorToken;
    }

    public void setAdministratorToken(String administratorToken) {
        this.administratorToken = administratorToken == null ? null : administratorToken.trim();
    }
}