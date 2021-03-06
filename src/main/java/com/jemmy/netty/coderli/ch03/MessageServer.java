/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MessageServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/5 21:51
 * Description: 
 */
package com.jemmy.netty.coderli.ch03;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * MessageServer
 *
 * @author Cheng Zhujiang
 * @date 2017/7/5
 */
public class MessageServer {

    public static void main(String[] args) {

        // Server服务启动器
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        );

        // 设置一个处理客户端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new MessageServerHandler());
            }
        });

        // 开放8000端口供客户端访问。
        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class MessageServerHandler extends SimpleChannelHandler {

        /**
         * 用户接受客户端发来的消息，在有客户端消息到达时触发
         *
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
            System.out.println(channelBuffer.toString(Charset.defaultCharset()));
        }
    }
}
