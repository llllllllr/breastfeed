package lllr.test.breast.dataObject.consult;

public class AutoAnswerTemplate {
    private Integer consultId;

    private String questionKey;

    private String answerTemplate;

    public AutoAnswerTemplate(){}

    @Override
    public String toString() {
        return "AutoAnswerTemplate{" +
                "consultId=" + consultId +
                ", questionKey='" + questionKey + '\'' +
                ", answerTemplate='" + answerTemplate + '\'' +
                '}';
    }

    public AutoAnswerTemplate(String questionKey, String answerTemplate) {
        this.questionKey = questionKey;
        this.answerTemplate = answerTemplate;
    }

    public AutoAnswerTemplate(Integer consultId, String questionKey, String answerTemplate) {
        this.consultId = consultId;
        this.questionKey = questionKey;
        this.answerTemplate = answerTemplate;
    }

    public Integer getConsultId() {
        return consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey == null ? null : questionKey.trim();
    }

    public String getAnswerTemplate() {
        return answerTemplate;
    }

    public void setAnswerTemplate(String answerTemplate) {
        this.answerTemplate = answerTemplate == null ? null : answerTemplate.trim();
    }
}