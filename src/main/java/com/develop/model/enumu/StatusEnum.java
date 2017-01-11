package com.develop.model.enumu;

public enum StatusEnum {
    SERVICE_SUCCESS(200, "成功"), UNAUTHORIZED_ERROR(4010, "未授权访问错误"),PARAM_ERROR(4600, "请求参数错误"), SERVER_ERROR(5220, "数据库错误"),
    MIDDLEWARE_ERROR(5400, "中间件错误"), NETWORK_ERROR(5490, "网络异常"), UNKNOWN_ERROR(5500, "未知错误"),
    SERVICE_ERROR(5210, "接口调用错误"), PARTITION_ERROR(5060, "部分操作错误"), PENDING(5061, "请求处理中，请稍后");

    private final int code;
    private final String desc;

    private StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
