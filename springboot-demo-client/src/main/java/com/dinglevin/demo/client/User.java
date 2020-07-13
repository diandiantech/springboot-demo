package com.dinglevin.demo.client;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
