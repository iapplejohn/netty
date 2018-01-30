/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: HelloServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/5 20:57
 * Description: 
 */
package com.jemmy.netty.coderli.ch01;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * HelloServer
 *
 * @author Cheng Zhujiang
 * @date 2017/7/5
 * @blog http://www.coderli.com
 */
public class HelloServer {

    public static void main(String[] args) {
        // Server服务启动器
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        );

        // 设置一个处理客户端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(
                new ChannelPipelineFactory() {
                    @Override
                    public ChannelPipeline getPipeline() throws Exception {
                        return Channels.pipeline(new HelloServerHandler());
                    }
                }
        );

        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class HelloServerHandler extends SimpleChannelHandler {

        /**
         * 当有客户端绑定到服务端的时候触发，打印"Hello world, I am server."
         *
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("Hello world, I am server.");
        }
    }
}
