package lllr.test.breast.dataObject.consult;

import java.util.Date;

public class WeChatMessageItem {
    private Integer id;

    private String fromUserId;

    private String toUserId;

    private Integer messageType;

    private String messageContent;

    private Date time;

    public WeChatMessageItem(){}

    public WeChatMessageItem(String fromUserId, String toUserId, Integer messageType, String messageContent,Date time) {
        this.time = time;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId == null ? null : fromUserId.trim();
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WeChatMessageItem{" +
                "id=" + id +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", messageType=" + messageType +
                ", messageContent='" + messageContent + '\'' +
                ", time=" + time +
                '}';
    }
}