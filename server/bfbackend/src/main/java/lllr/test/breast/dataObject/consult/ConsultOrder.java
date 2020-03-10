package lllr.test.breast.dataObject.consult;

import lllr.test.breast.dataObject.user.Doctor;
import lllr.test.breast.dataObject.user.User;

import javax.print.Doc;
import java.util.Date;

public class ConsultOrder {
    private Integer id;

    private Integer doctorId;

    private Integer userId;

    private Date createTime;

    private Integer lastingTime;

    private String contact;

    private String contactPhone;

    private String symptomDescription;

    private User user;

    private Doctor doctor;

    private Integer consultCost;

    private Integer status;

    private String oid;

    private String userOpenId;

    private String doctorOpenId;

    private String imgUrls;

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getDoctorOpenId() {
        return doctorOpenId;
    }

    public void setDoctorOpenId(String doctorOpenId) {
        this.doctorOpenId = doctorOpenId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "ConsultOrder{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", lastingTime=" + lastingTime +
                ", contact='" + contact + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", symptomDescription='" + symptomDescription + '\'' +
                ", user=" + user +
                ", doctor=" + doctor +
                ", consultCost=" + consultCost +
                ", status=" + status +
                ", oid='" + oid + '\'' +
                ", userOpenId='" + userOpenId + '\'' +
                ", doctorOpenId='" + doctorOpenId + '\'' +
                ", imgUrls='" + imgUrls + '\'' +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getConsultCost() {
        return consultCost;
    }

    public void setConsultCost(Integer consultCost) {
        this.consultCost = consultCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ConsultOrder(){}

    public ConsultOrder(Integer doctorId, Integer userId, Date createTime, Integer lastingTime, String contact, String contactPhone, String symptomDescription,Integer consultCost) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.createTime = createTime;
        this.lastingTime = lastingTime;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.symptomDescription = symptomDescription;
        this.consultCost = consultCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastingTime() {
        return lastingTime;
    }

    public void setLastingTime(Integer lastingTime) {
        this.lastingTime = lastingTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription == null ? null : symptomDescription.trim();
    }
}