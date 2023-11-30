package com.example.searchphone.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    /**
     * status : 当前响应状态
     * msg ：   当前响应信息
     * data ：  当前响应数据
     * count ： 当前响应数据个数
     */
    private Integer status;
    private String msg;
    private T data;
    private Integer count;

    public Response(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this(code,msg);
        this.data = data;
    }

    public Response(int code, String msg, T data, Integer count) {
        this(code,msg,data);
        this.count = count;
    }

    public Response responseOk(T data) {
        return new Response<T>(200, "ok", data);
    }




}