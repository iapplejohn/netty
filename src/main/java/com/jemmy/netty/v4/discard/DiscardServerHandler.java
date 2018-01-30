/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: DiscardServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/17 11:47
 * Description: 
 */
package com.jemmy.netty.v4.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <pre>
 * Handles a server-side channel.
 *
 * @author Cheng Zhujiang
 * @date 2017/11/17
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Discard the received data silently.
        ByteBuf in = (ByteBuf) msg;

        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
