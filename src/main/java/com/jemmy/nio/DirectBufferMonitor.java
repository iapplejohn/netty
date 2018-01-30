/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: DirectBufferMonitor.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/16 22:34
 * Description: 
 */
package com.jemmy.nio;

import java.lang.reflect.Field;

/**
 * <pre>
 * DirectBuffer监控
 *
 * @author Cheng Zhujiang
 * @date 2017/12/16
 */
public class DirectBufferMonitor {

    public void monDirectBuffer() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        Class c = Class.forName("java.nio.Bits"); // 通过反射取得私有数据

        Field maxMemory = c.getDeclaredField("maxMemory");
        maxMemory.setAccessible(true);
        Field reservedMemory = c.getDeclaredField("reservedMemory");
        reservedMemory.setAccessible(true);
        synchronized (c) {
            Long maxMemoryValue = (Long) maxMemory.get(null); // 总大小
            Long reservedMemoryValue = (Long) reservedMemory.get(null); // 剩余大小
            System.out.println("maxMemoryValue:" + maxMemoryValue);
            System.out.println("reservedMemoryValue:" + reservedMemoryValue);
        }
    }

}
