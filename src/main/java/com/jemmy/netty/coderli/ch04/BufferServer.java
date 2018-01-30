/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: BufferServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/5 22:13
 * Description: 
 */
package com.jemmy.netty.coderli.ch04;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * BufferServer
 *
 * @author Cheng Zhujiang
 * @date 2017/7/5
 */
public class BufferServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        );

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ServerBufferHandler());
            }
        });

        bootstrap.bind(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class ServerBufferHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
            // 五位读取
            while (channelBuffer.readableBytes() >= 5) {
                ChannelBuffer tempBuffer = channelBuffer.readBytes(5);
                System.out.println(tempBuffer.toString(Charset.defaultCharset()));
            }

            // 读取剩下的信息
            System.out.println(channelBuffer.toString(Charset.defaultCharset()));
        }
    }
}
