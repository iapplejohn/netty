/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: QuoteOfTheMomentServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 9:16
 * Description: 
 */
package com.jemmy.netty.v4.qotm;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * <pre>
 * QuoteOfTheMomentServer
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class QuoteOfTheMomentServer {

    private static final int PORT = Integer.parseInt(System.getProperty("port", "7686"));

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new QuoteOfTheMomentServerHandler());

            b.bind(PORT).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }
}
