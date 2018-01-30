/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 22:24
 * Description: 
 */
package com.jemmy.netty.guide.ch05;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * <pre>
 * EchoClient
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                     ChannelPipeline p = ch.pipeline();
                     //p.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                     p.addLast(new FixedLengthFrameDecoder(20));
                     p.addLast(new StringDecoder());
                     p.addLast(new EchoClientHandler());
                 }
             });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8988;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new EchoClient().connect("127.0.0.1", port);
    }
}
