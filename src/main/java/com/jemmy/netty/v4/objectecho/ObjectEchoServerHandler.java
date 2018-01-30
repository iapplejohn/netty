/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: ObjectEchoServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 11:32
 * Description: 
 */
package com.jemmy.netty.v4.objectecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the client.
        System.err.println("Received: " + msg);
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
