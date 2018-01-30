/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/18 19:45
 * Description: 
 */
package com.jemmy.netty.v4.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 * EchoServerHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/11/18
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;

        System.out.print("Received: ");
        while (in.isReadable()) {
            System.out.print(in.readByte());
        }
        System.out.println();

        in.discardReadBytes();
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
