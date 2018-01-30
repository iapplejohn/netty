/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TimeServerV2Handler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 18:10
 * Description: 
 */
package com.jemmy.netty.guide.ch04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * <pre>
 * TimeServerV2Handler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class TimeServerV2Handler extends SimpleChannelInboundHandler<String> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("The time server receives order: " + msg + "; the counter is: " + ++count);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(msg) ?
                new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime += System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
