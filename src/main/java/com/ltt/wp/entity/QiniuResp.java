package com.ltt.wp.entity;

import lombok.Data;

/**
 * 七牛云响应
 */
@Data
public class QiniuResp {

    // {"hash":"FjYS2zjymQmQ6WhS8ePpnnbKZEry","key":"ButtermereSunset.jpg"}

    private String hash;

    private String key;
}
