/*
 * Copyright (C), 2017, 杭州青葱科技有限公司
 * FileName: TelnetServerInitializer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/29 22:10
 * Description: 
 */
package com.jemmy.netty.v4.telnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * <pre>
 * TelnetServerInitializer
 *
 * @author Cheng Zhujiang
 * @date 2017/11/29
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final TelnetServerHandler SERVER_HANDLER = new TelnetServerHandler();

    private final SslContext sslCtx;

    public TelnetServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }

        // Add the text line codec combination first,
        p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // the encoder and decoder are static as these are sharable
        p.addLast(DECODER);
        //p.addLast(ENCODER);

        // and then business logic.
        p.addLast(SERVER_HANDLER);
    }
}
