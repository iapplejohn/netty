package com.jemmy.netty.memory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * -Dio.netty.leakDetectionLevel=paranoid
 *
 * @author zhujiang.cheng
 * @since 2021/3/9
 */
public class LeakDetectionTest {

    public static void main(String[] args) {
        for (int i = 0; i < 500000; i++) {
            ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(1024);
            byteBuf = null;
        }
        System.gc();
    }

}
