/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 15:23
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 * EchoServerHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive the msgpack message : " + msg);
        ctx.write(msg);
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
