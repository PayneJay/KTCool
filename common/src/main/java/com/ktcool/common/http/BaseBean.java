package com.ktcool.common.http;

import java.io.Serializable;

/**
 * 网络请求返回数据模型基类
 */
public class BaseBean implements Serializable {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
