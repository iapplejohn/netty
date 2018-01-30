/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TimeClientV2Handler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 18:12
 * Description: 
 */
package com.jemmy.netty.guide.ch04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * TimeClientV2Handler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class TimeClientV2Handler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeClientV2Handler.class);

    private int counter;

    private byte[] req;

    public TimeClientV2Handler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Now is: " + msg + " ; the counter is: " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 释放资源
        LOGGER.warn("Unexpected exception from downstream: " + cause.getMessage());
        ctx.close();
    }
}
