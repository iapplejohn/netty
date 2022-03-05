/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TimeServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 20:14
 * Description: 
 */
package com.jemmy.netty.guide.ch04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * <pre>
 * TimeServer
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class TimeServer {

    public void bind(int port) throws InterruptedException {
        // 1. 配置服务端的NIO线程组: bossGroup用于接收连接，workGroup用于具体的处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 创建服务端启动引导/辅助类: ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();

            // 3. 给引导类配置两大线程组，确定了线程模型
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 1024)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new TimeServerHandler());
                 }
             });

            // 6. Start the server
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeServer().bind(6768);
    }
}
