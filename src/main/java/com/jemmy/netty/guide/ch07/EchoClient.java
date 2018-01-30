/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/1 9:17
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <pre>
 * EchoClient
 *
 * @author Cheng Zhujiang
 * @date 2017/12/1
 */
public class EchoClient {

    private final String host;
    private final int port;
    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast("msgpack decoder", new MsgpackDecoder());
                     p.addLast("msgpack encoder", new MsgpackEncoder());
                     p.addLast(new EchoClientHandler(sendNumber));
                 }
             });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("127.0.0.1", 8339, 1000).run();
    }
}
