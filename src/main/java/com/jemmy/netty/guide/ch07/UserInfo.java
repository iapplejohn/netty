/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: UserInfo.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 15:14
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * <pre>
 * UserInfo
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
@Message
public class UserInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
