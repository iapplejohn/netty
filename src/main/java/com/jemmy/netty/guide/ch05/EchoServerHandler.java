/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 22:15
 * Description: 
 */
package com.jemmy.netty.guide.ch05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <pre>
 * EchoServerHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("This is " + ++counter + " times receive client : [" + msg + "]");
        msg += "$_";
        ByteBuf echo = Unpooled.copiedBuffer(msg.getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
