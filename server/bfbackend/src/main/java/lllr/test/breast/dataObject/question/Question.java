package lllr.test.breast.dataObject.question;

public class Question {
    private Integer qId;

    private Integer tId;

    private Integer qType;

    private String qContent;

    private String qOpstions;

    public Integer getqId() {
        return qId;
    }

    public void setqId(Integer qId) {
        this.qId = qId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer getqType() {
        return qType;
    }

    public void setqType(Integer qType) {
        this.qType = qType;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent == null ? null : qContent.trim();
    }

    public String getqOpstions() {
        return qOpstions;
    }

    public void setqOpstions(String qOpstions) {
        this.qOpstions = qOpstions == null ? null : qOpstions.trim();
    }
}