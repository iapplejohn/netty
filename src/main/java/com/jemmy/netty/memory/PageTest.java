package com.jemmy.netty.memory;

import sun.jvm.hotspot.utilities.Bits;
import sun.misc.VM;

/**
 * @author zhujiang.cheng
 * @since 2021/3/10
 */
public class PageTest {

    public static void main(String[] args) {
        boolean pa = VM.isDirectMemoryPageAligned(); //是否页对齐

        System.out.println("是否页对齐: " + pa);
    }

}
