package com.dgl.www.twodimensioncode.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by dugaolong on 17/6/1.
 */

public class QrCode extends DataSupport implements Serializable {

    private long time;
    private int type;//1:链接，2：文本
    private String Content;
    private byte[] blob;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }
}
