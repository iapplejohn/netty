/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: BufferTest.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/7 19:43
 * Description: 
 */
package com.jemmy.nio;

import org.jboss.netty.util.internal.ByteBufferUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * BufferTest
 *
 * @author Cheng Zhujiang
 * @date 2017/8/7
 */
public class BufferTest {

    public static void test() {
        ByteBuffer b = ByteBuffer.allocate(15); // 15个字节大小的缓冲区
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());

        for (int i = 0; i < 10; i++) { // 存入10个字节数据
            b.put((byte) i);
        }
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());

        b.flip(); // 重置position
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());

        for (int i = 0; i < 5; i++) {
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());

        b.flip();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());
    }

    // 标志缓冲区
    public static void testMark() {
        ByteBuffer b = ByteBuffer.allocate(15);
        for (int i = 0; i < 10; i++) {
            b.put((byte) i);
        }

        b.flip(); // 准备读
        for (int i = 0 ; i < b.limit(); i++) {
            System.out.print(b.get());
            if (i == 4) {
                b.mark();
                System.out.print("(mark at " + i + ")");
            }
        }
        b.reset();
        System.out.println("\nreset to mark");
        while (b.hasRemaining()) {
            System.out.print(b.get());
        }
        System.out.println();
    }

    // 5.复制缓冲区
    public static void testDuplicate() {
        ByteBuffer b = ByteBuffer.allocate(15);
        for (int i = 0; i < 10; i++) {
            b.put((byte) i);
        }

        ByteBuffer c = b.duplicate();
        System.out.println("After b.duplicate()");
        System.out.println(b);
        System.out.println(c);

        c.flip();
        System.out.println("After c.flip()");
        System.out.println(b);
        System.out.println(c);

        c.put((byte)100);
        System.out.println("After c.put((byte) (100))");
        System.out.println("b.get(0)=" + b.get(0));
        System.out.println("c.get(0)=" + c.get(0));
    }

    // 6. 缓冲区切片
    public static void testSlice() {
        ByteBuffer b = ByteBuffer.allocate(15); // 分配15个字节的空间
        for (int i = 0; i < 10; i++) {
            b.put((byte)i); // 填充数据
        }
        b.position(2);
        b.limit(6);
        ByteBuffer subBuffer = b.slice(); // 生成子缓冲区

        for (int i = 0; i < subBuffer.capacity(); i++) {
            byte bb = subBuffer.get();
            bb *= 10; // 在子缓冲区中，将每一个元素都乘以10
            subBuffer.put(i, bb);
        }

        b.position(0);
        b.limit(b.capacity()); // 重置父缓冲区，并查看父缓冲区的数据
        while (b.hasRemaining()) {
            System.out.print(b.get() + " ");
        }
    }

    // 7. 只读缓冲区
    public static void testReadOnly() {
        ByteBuffer b = ByteBuffer.allocate(15);
        for (int i = 0; i < 10; i++) {
            b.put((byte)i);
        }
        ByteBuffer readOnly = b.asReadOnlyBuffer(); // 创建只读缓冲区
        readOnly.flip();
        while (readOnly.hasRemaining()) {
            System.out.print(readOnly.get() + " ");
        }
        System.out.println();

        b.put(2, (byte)20); // 修改原始缓冲区的数据
        // readOnly.put(2, (byte)20); // 此处会抛出ReadOnlyBufferException

        readOnly.flip();
        while (readOnly.hasRemaining()) {
            System.out.print(readOnly.get() + " "); // 新的改动，在只读缓冲区内可见
        }
    }

    // 8. 文件映射到内存
    public static void testMapped() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("E:\\tmp\\mapfile.txt", "rw");
        FileChannel fc = raf.getChannel();

        // 将文件映射到内存中
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
        while (mbb.hasRemaining()) {
            System.out.print((char)mbb.get());
        }
        mbb.put(0, (byte)98); // 不能超过原有长度
        raf.close(); // 修改文件
    }

    // 9. 处理结构化数据
    public static void testScatterAndGather() throws IOException {
        String filePath = "E:/tmp/data.txt";

        ByteBuffer bookBuf = ByteBuffer.wrap("Java 大数据编程".getBytes("utf-8"));
        ByteBuffer authorBuf = ByteBuffer.wrap("程为俊".getBytes("utf-8"));
        int bookLen = bookBuf.limit(); // 记录书名长度
        int authorLen = authorBuf.limit(); // 记录作者长度
        ByteBuffer[] bufs = new ByteBuffer[] { bookBuf, authorBuf };
        File file = new File(filePath);
        if (!file.exists()) { // 文件不存在则创建文件
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel fc = fos.getChannel();
        fc.write(bufs); // 聚集写文件
        fos.close();

        ByteBuffer b1 = ByteBuffer.allocate(bookLen);
        ByteBuffer b2 = ByteBuffer.allocate(authorLen);
        ByteBuffer[] buffers = new ByteBuffer[] { b1, b2 };
        File file2 = new File(filePath);
        FileInputStream fis = new FileInputStream(file2);
        FileChannel fc2 = fis.getChannel();
        fc2.read(buffers); // 散射读
        String bookName = new String(buffers[0].array(), "utf-8");
        String authorName = new String(buffers[1].array(), "utf-8");
        System.out.println(bookName + authorName);
    }

    public static void main(String[] args) throws IOException {
//        test();
//        testMark();
//        testDuplicate();
//        testSlice();
//        testReadOnly();
//        testMapped();
        testScatterAndGather();
    }
}
