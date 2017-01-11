/*
 * Copyright 2012-2014 Wanda.cn All right reserved. This software is the
 * confidential and proprietary information of Wanda.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Wanda.cn.
 */
package com.develop.model.web;

import java.io.Serializable;

import com.develop.model.enumu.StatusEnum;
import com.develop.model.util.JSONUtil;

/**
 * 类ResponseVo.java的实现描述：请求返回对象
 * 
 * @author huhuichao 2014-8-14 下午4:18:10
 */
public class ResponseVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3073226328007685525L;

    private int status;

    private String message;

    private Object data;
    
    private Long timeCost;
    
    private MetadataVo metadata;

    public ResponseVo() {

    }

    public ResponseVo(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

  

    public ResponseVo(StatusEnum statusEnum, Object data) {
        this.status = statusEnum.getCode();
        this.message = statusEnum.getDesc();
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseVo [status=" + status + ", message=" + message
                + ", data=" + JSONUtil.bean2JSONObject(data) + "]";
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }
    
//    @JsonProperty("_metadata")
    public MetadataVo getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataVo metadata) {
        this.metadata = metadata;
    }

}
