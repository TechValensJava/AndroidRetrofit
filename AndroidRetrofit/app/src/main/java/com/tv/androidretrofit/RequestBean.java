package com.tv.androidretrofit;

/**
 * Created by anjali on 3/3/16.
 */
public class RequestBean {
    private String phone;
   private  String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RequestBean(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
