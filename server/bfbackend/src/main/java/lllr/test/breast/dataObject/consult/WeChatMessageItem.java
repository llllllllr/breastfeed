package lllr.test.breast.dataObject.consult;

import java.util.Date;

public class WeChatMessageItem {
    private Integer id;

    private Integer fromUserId;

    private Integer toUserId;

    private Integer messageType;

    private String messageContent;

    private Date time;

    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public WeChatMessageItem(){}

    @Override
    public String toString() {
        return "WeChatMessageItem{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", messageType=" + messageType +
                ", messageContent='" + messageContent + '\'' +
                ", time=" + time +
                ", oid='" + oid + '\'' +
                '}';
    }

    public WeChatMessageItem(Integer fromUserId, Integer toUserId, int messageType, String messageContent, Date time) {
        this.time = time;
        this.fromUserId =fromUserId;
        this.toUserId = toUserId;
        this.messageContent = messageContent;
        this.messageType = messageType;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
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
}