/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: ObjectEchoClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 14:40
 * Description: 
 */
package com.jemmy.netty.v4.objectecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * ObjectEchoClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    private final List<Integer> firstMessage;

    public ObjectEchoClientHandler() {
        firstMessage = new ArrayList<>(ObjectEchoClient.SIZE);
        for (int i = 0; i < ObjectEchoClient.SIZE; i++) {
            firstMessage.add(i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the first message if this handler is a client-side handler.
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the server.
        System.err.println("Received from server: " + msg);
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
