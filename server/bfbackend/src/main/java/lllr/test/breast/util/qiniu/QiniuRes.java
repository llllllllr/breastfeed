package lllr.test.breast.util.qiniu;

import java.io.Serializable;

public class QiniuRes implements Serializable {

    private Integer errno;
    private String[] data;

    public QiniuRes(Integer errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
