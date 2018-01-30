/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServerV2.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 16:41
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * <pre>
 * EchoServerV2
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class EchoServerV2 {

    private int port;

    public EchoServerV2(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                     p.addLast("msgpack decoder", new MsgpackDecoder());
                     p.addLast("frameEncoder", new LengthFieldPrepender(2));
                     p.addLast("msgpack encoder", new MsgpackEncoder());
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
        new EchoServerV2(8359).run();
    }
}
