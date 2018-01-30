/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TelnetClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/30 19:21
 * Description: 
 */
package com.jemmy.netty.v4.telnet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

/**
 * <pre>
 * TelnetClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/11/30
 */
@ChannelHandler.Sharable
public class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
