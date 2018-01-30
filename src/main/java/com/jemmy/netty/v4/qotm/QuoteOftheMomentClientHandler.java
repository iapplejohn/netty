/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: QuoteOftheMomentClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 9:49
 * Description: 
 */
package com.jemmy.netty.v4.qotm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * <pre>
 * QuoteOftheMomentClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class QuoteOftheMomentClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith("QOTM: ")) {
            System.out.println("Quote of the Moment: " + response.substring(6));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
