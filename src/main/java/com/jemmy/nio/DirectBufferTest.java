/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: DirectBufferTest.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/16 20:09
 * Description: 
 */
package com.jemmy.nio;

import java.nio.ByteBuffer;

/**
 * <pre>
 * 3.3.5 直接内存访问
 *
 * @author Cheng Zhujiang
 * @date 2017/12/16
 */
public class DirectBufferTest {

    public static void main(String[] args) {
        long start = System.nanoTime();
        testDirectBuffer();
        long end1 = System.nanoTime();
        System.out.println("testDirectBuffer cost: " + (end1 - start));
        testByteBuffer();
        long end2 = System.nanoTime();
        System.out.println("testByteBuffer cost: " + (end2 - end1));
    }

    private static void testDirectBuffer() {
        ByteBuffer b = ByteBuffer.allocateDirect(500); // 分配DirectBuffer
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                b.putInt(j); // 向DirectBuffer中写入数据
            }
            b.flip();
            for (int j = 0; j < 99; j++) {
                b.getInt();
            }
            b.clear();
        }
    }

    private static void testByteBuffer() {
        ByteBuffer b = ByteBuffer.allocate(500); // 分配heap buffer
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                b.putInt(j); // 向heap buffer中写入数据
            }
            b.flip();
            for (int j = 0; j < 99; j++) {
                b.getInt();
            }
            b.clear();
        }
    }
}
