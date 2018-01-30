/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: NioTest.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/7 19:34
 * Description: 
 */
package com.jemmy.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NioTest
 *
 * @author Cheng Zhujiang
 * @date 2017/8/7
 */
public class NioTest {

    public static void nioCopyFile(String resource, String destination) throws IOException {
        FileInputStream fis = new FileInputStream(resource);
        FileOutputStream fos = new FileOutputStream(destination);
        FileChannel readChannel = fis.getChannel(); // 读文件通道
        FileChannel writeChannel = fos.getChannel(); // 写文件通道
        ByteBuffer buffer = ByteBuffer.allocate(1024); // 读入数据缓存
        while (true) {
            buffer.clear();
            int len = readChannel.read(buffer); // 读入数据
            if (len == -1) { // 读取完毕
                break;
            }

            buffer.flip();
            writeChannel.write(buffer); // 写入文件
        }
        readChannel.close();
        writeChannel.close();
    }

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        String source = "E:\\books\\MongoDB\\MongoDB-manual-master.pdf";
        String destination = "F:\\MongoDB-manual-master.pdf";
        nioCopyFile(source, destination);
        System.out.println("Cost:" + (System.nanoTime() - start));
    }
}
