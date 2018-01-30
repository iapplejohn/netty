/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TelnetClientInitializer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/30 8:24
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
 * TelnetClientInitializer
 *
 * @author Cheng Zhujiang
 * @date 2017/11/30
 */
public class TelnetClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final TelnetClientHandler CLIENT_HANDLER = new TelnetClientHandler();

    private final SslContext sslCtx;

    public TelnetClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc(), TelnetClient.HOST, TelnetClient.PORT));
        }

        // Add the text line codec combination first,
        p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        p.addLast(DECODER);
        //p.addLast(ENCODER);

        // and then business logic.
        p.addLast(CLIENT_HANDLER);
    }
}
