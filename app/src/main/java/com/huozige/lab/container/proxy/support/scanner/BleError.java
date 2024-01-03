package com.huozige.lab.container.proxy.support.scanner;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.clj.fastble.exception.BleException;

public class BleError {
    public int code;
    public String description;

    public BleError(int code, String desc){
        this.code = code;
        this.description = desc;
    }

    public static BleError from(BleException ex){
        return  new BleError( ex.getCode(), ex.getDescription());
    }

    @NonNull
    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
