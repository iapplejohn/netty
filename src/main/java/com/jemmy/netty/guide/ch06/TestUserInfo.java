/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TestUserInfo.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 9:35
 * Description: 
 */
package com.jemmy.netty.guide.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * <pre>
 * TestUserInfo
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(info);
        oos.flush();;
        oos.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serialization length : " + b.length);
        bos.close();
        System.out.println("-------------------------------");
        System.out.println("The byte array serialization length : " + info.codeC().length);
    }

}
