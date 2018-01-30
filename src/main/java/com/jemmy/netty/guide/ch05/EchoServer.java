/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 20:19
 * Description: 
 */
package com.jemmy.netty.guide.ch05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * <pre>
 * EchoServer
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoServer {

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                     ChannelPipeline p = ch.pipeline();
                     //p.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                     p.addLast(new FixedLengthFrameDecoder(20));
                     p.addLast(new StringDecoder());
                     p.addLast(new EchoServerHandler());
                 }
             });

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
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

        new EchoServer().bind(port);
    }
}
