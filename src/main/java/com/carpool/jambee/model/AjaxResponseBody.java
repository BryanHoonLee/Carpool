package com.carpool.jambee.model;

import java.util.List;

public class AjaxResponseBody {
    String msg;
    List<User> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
