/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: HttpResponseClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 11:11
 * Description: 
 */
package com.jemmy.netty.v4.spdy.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <pre>
 * HttpResponseClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class HttpResponseClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final BlockingQueue<ChannelFuture> queue = new LinkedBlockingDeque<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

    }

    public BlockingQueue<ChannelFuture> queue() {
        return queue;
    }
}
