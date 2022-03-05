/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TimeClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/28 10:55
 * Description: 
 */
package com.jemmy.netty.guide.ch04;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * <pre>
 * TimeClient
 *
 * @author Cheng Zhujiang
 * @date 2017/11/28
 */
public class TimeClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建客户端启动引导类: Bootstrap
            Bootstrap b = new Bootstrap();

            // 指定线程模型
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new TimeClientHandler());
                 }
             });

            // 尝试建立连接
            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            // 优雅关闭相关线程组资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeClient().connect("127.0.0.1", 6768);
    }
}
