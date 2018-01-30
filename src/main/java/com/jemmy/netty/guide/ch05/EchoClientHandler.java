/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 22:27
 * Description: 
 */
package com.jemmy.netty.guide.ch05;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <pre>
 * EchoClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<String> {

    private int counter;

    private final String ECHO_REQ = "Hi John. Welcome to Netty.$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
