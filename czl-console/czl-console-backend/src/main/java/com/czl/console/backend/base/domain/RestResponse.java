package com.czl.console.backend.base.domain;

import java.util.HashMap;

public class RestResponse extends HashMap<String,Object> {
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAILURE = "failure";

    private static final long serialVersionUID = -8713837118340960775L;

    public static RestResponse success(Object data) {
        RestResponse resp = new RestResponse();
        resp.put("result", STATUS_SUCCESS);
        resp.put("data", data);
        return resp;
    }

    public static RestResponse success() {
        RestResponse resp = new RestResponse();
        resp.put("result", STATUS_SUCCESS);
        return resp;
    }


    public static RestResponse fail(String message) {
        RestResponse resp = new RestResponse();
        resp.put("result", STATUS_FAILURE);
        resp.put("message", message);
        resp.put("data", null);
        return resp;
    }

    public static RestResponse create() {
        return new RestResponse();
    }

    public RestResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public RestResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public RestResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
