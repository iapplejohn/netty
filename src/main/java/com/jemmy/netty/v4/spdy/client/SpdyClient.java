/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SpdyClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 10:42
 * Description: 
 */
package com.jemmy.netty.v4.spdy.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * <pre>
 * SpdyClient
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public final class SpdyClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");

    static final int PORT = Integer.parseInt(System.getProperty("port", "8443"));

    public static void main(String[] args) throws SSLException, InterruptedException {
        // Configure SSL.
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .applicationProtocolConfig(new ApplicationProtocolConfig(
                        Protocol.NPN,
                        // NO_ADVERTISE is currently the only mode supported by both OpenSsl and JDK providers.
                        SelectorFailureBehavior.NO_ADVERTISE,
                        // ACCEPT is currently the only mode supported by both OpenSsl and JDK providers.
                        SelectedListenerFailureBehavior.ACCEPT,
                        ApplicationProtocolNames.SPDY_3_1,
                        ApplicationProtocolNames.HTTP_1_1
                )).build();

        HttpResponseClientHandler httpResponseHandler = new HttpResponseClientHandler();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.SO_KEEPALIVE, true)
             .remoteAddress(HOST, PORT)
             .handler(new SpdyClientInitializer(sslCtx, httpResponseHandler));

            // Start the client
            Channel channel = b.connect().syncUninterruptibly().channel();
            System.out.println("Connected to " + HOST + ":" + PORT);

            // Create a GET request.
            HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "");
            request.headers().set(HttpHeaderNames.HOST, HOST);
            request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

            // Send the GET request.
            channel.writeAndFlush(request).sync();

            // Waits for the complete HTTP response
            httpResponseHandler.queue().take().sync();
            System.out.println("Finished SPDY HTTP GET");

            // Wait until the connection is closed.
            channel.close().syncUninterruptibly();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
