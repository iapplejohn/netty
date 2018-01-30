/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: PerformTestUserInfo.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 9:46
 * Description: 
 */
package com.jemmy.netty.guide.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * <pre>
 * PerformTestUserInfo
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class PerformTestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        int loop = 1000000;
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(info);
            oos.flush();
            oos.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serialization cost time is : " + (endTime - startTime) + " ms");
        System.out.println("-----------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = info.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serialization cost time is : " + (endTime - startTime) + " ms");
    }

}
