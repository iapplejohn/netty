/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SpdyClientInitializer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 11:11
 * Description: 
 */
package com.jemmy.netty.v4.spdy.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.spdy.SpdyFrameCodec;
import io.netty.handler.codec.spdy.SpdySessionHandler;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.handler.ssl.SslContext;

import static io.netty.util.internal.logging.InternalLogLevel.INFO;

/**
 * <pre>
 * SpdyClientInitializer
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class SpdyClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_SPDY_CONTENT_LENGTH = 1024 * 1024; // 1 MB

    private final SslContext sslCtx;

    private final HttpResponseClientHandler httpResponseHandler;

    public SpdyClientInitializer(SslContext sslCtx, HttpResponseClientHandler httpResponseHandler) {
        this.sslCtx = sslCtx;
        this.httpResponseHandler = httpResponseHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("ssl", sslCtx.newHandler(ch.alloc()));
        p.addLast("spdyFrameCodec", new SpdyFrameCodec(SpdyVersion.SPDY_3_1));
        p.addLast("spdyFrameLogger", new SpdyFrameLogger(INFO));
        p.addLast("spdySessionHandler", new SpdySessionHandler(SpdyVersion.SPDY_3_1, false));
        // TODO
    }
}
