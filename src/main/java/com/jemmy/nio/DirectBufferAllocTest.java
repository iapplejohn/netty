/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: DirectBuffer2Test.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/16 22:24
 * Description: 
 */
package com.jemmy.nio;

import java.nio.ByteBuffer;

/**
 * <pre>
 * DirectBuffer分配和销毁测试
 * 使用MaxDirectMemorySize可以指定DirectBuffer的最大可用空间，DirectBuffer
 * 的缓存空间不在堆上分配，因此可以使应用程序突破最大堆的内存限制。
 * 对DirectBuffer的读写操作比普通Buffer快，但是对它的创建和销毁却比普通Buffer慢。
 *
 * @author Cheng Zhujiang
 * @date 2017/12/16
 */
public class DirectBufferAllocTest {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        // 直接在内存中分配Buffer
//        long start = System.nanoTime();
//        testDirectBufferAlloc();
        long end1 = System.nanoTime();
//        System.out.println("testDirectBufferAlloc cost: " + (end1 - start));

        // 在堆上分配Buffer
        testNormalBufferAlloc();
        long end2 = System.nanoTime();
        System.out.println("testNormalBufferAlloc cost: " + (end2 - end1));
        DirectBufferMonitor monitor = new DirectBufferMonitor();
        monitor.monDirectBuffer();
    }

    private static void testNormalBufferAlloc() {
        for (int i = 0; i < 20000; i++) {
            ByteBuffer b = ByteBuffer.allocate(1000); // 创建heap Buffer
        }
    }

    private static void testDirectBufferAlloc() {
        for (int i = 0; i < 20000; i++) {
            ByteBuffer b = ByteBuffer.allocateDirect(1000); // 创建DirectBuffer
        }
    }
}
