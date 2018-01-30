/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MappedByteBufferTest.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/13 11:11
 * Description: 
 */
package com.jemmy.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBufferTest
 *
 * @author Cheng Zhujiang
 * @date 2017/8/13
 */
public class MappedByteBufferTest {

    private static final int numOfInts = 4000000;

    public static void testStreamWrite() throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(new File("E:\\tmp\\temp_stream.tmp"))
        ));
        for (int i = 0; i < numOfInts; i++) { // numOfInts=400万
            dos.writeInt(i); // 这里向文件中写入400万个整数
        }
        dos.close();
    }

    public static void testStreamRead() throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(new File("E:\\tmp\\temp_stream.tmp"))
        ));
        for (int i = 0; i < numOfInts; i++) {
            dis.readInt();
        }
        dis.close();
    }

    public static void testByteBufferWrite() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("E:\\tmp\\temp_stream.tmp"));
        FileChannel fc = fos.getChannel(); // 得到通道
        ByteBuffer buffer = ByteBuffer.allocate(numOfInts * 4); // 分配Buffer
        for (int i = 0; i < numOfInts; i++) {
            buffer.put(int2byte(i)); // 将整数转为字节
        }
        buffer.flip(); // 准备写
        fc.write(buffer);
        fc.close();
    }

    public static void testByteBufferRead() throws IOException {
        FileInputStream fis = new FileInputStream(new File("E:\\tmp\\temp_stream.tmp"));
        FileChannel fc = fis.getChannel(); // 取得文件通道
        ByteBuffer buffer = ByteBuffer.allocate(numOfInts * 4);
        fc.read(buffer); // 读取文件数据
        fc.close();
        buffer.flip(); // 准备读取数据
        while (buffer.hasRemaining()) {
            byte2int(buffer.get(), buffer.get(), buffer.get(), buffer.get()); // 将byte转为整数
        }
    }

    private static int byte2int(byte b1, byte b2, byte b3, byte b4) {
        return ((b1 & 0xff) << 24) | ((b2 & 0xff) << 16)
                | ((b3 & 0xff) << 8) | (b4 & 0xff);
    }

    private static byte[] int2byte(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xff); // 最低位
        targets[2] = (byte) ((i >> 8) & 0xff); // 次低位
        targets[1] = (byte) ((i >> 16) & 0xff); // 次高位
        targets[0] = (byte) (i >>> 24); // 最高位，无符号右移
        return targets;
    }

    public static void testMappedWrite() throws IOException {
        FileChannel fc = new RandomAccessFile("E:\\tmp\\temp_mapped.tmp", "rw").getChannel();
        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, numOfInts * 4).asIntBuffer();
        for (int i = 0; i < numOfInts; i++) {
            ib.put(i);
        }
        fc.close();
    }

    public static void testMappedRead() throws IOException {
        FileChannel fc = new RandomAccessFile("E:\\tmp\\temp_mapped.tmp", "rw").getChannel();
        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer(); // 文件映射到内存
        while (ib.hasRemaining()) {
            ib.get(); // 读取所有数据
        }
        fc.close();
    }

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        testStreamWrite();
        long end1 = System.nanoTime();
        System.out.println("testSteamWrite cost: " + (end1 - start));
        testStreamRead();
        long end2 = System.nanoTime();
        System.out.println("testStreamRead cost: " + (end2 - end1));
        testByteBufferWrite();
        long end3 = System.nanoTime();
        System.out.println("testByteBufferWrite cost: " + (end3 - end2));
        testByteBufferRead();
        long end4 = System.nanoTime();
        System.out.println("testByteBufferRead cost: " + (end4 - end3));
        testMappedWrite();
        long end5 = System.nanoTime();
        System.out.println("testMappedWrite cost: " + (end5 - end4));
        testMappedRead();
        long end6 = System.nanoTime();
        System.out.println("testMappedRead cost: " + (end6 - end5));
    }

}
