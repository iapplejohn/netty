/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: WebSocketServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/5 22:06
 * Description: 
 */
package com.jemmy.netty.guide.ch11;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <pre>
 * WebSocketServer
 *
 * @author Cheng Zhujiang
 * @date 2017/12/5
 */
public class WebSocketServer {

    public void run(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast("http-codec", new HttpServerCodec());
                     p.addLast("aggregator", new HttpObjectAggregator(65536));
                     p.addLast("http-chunked", new ChunkedWriteHandler());
                     p.addLast("handler", new WebSocketServerHandler());
                 }
             });

            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web socket server started at port " + '.');
            System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new WebSocketServer().run(port);
    }
}
