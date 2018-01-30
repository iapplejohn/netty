/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: OioServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/5 16:05
 * Description: 
 */
package com.jemmy.netty.coderli.ch14;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.oio.OioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * OioServer
 *
 * @author Cheng Zhujiang
 * @date 2017/8/5
 */
public class OioServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new OioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        // Executors.newCachedThreadPool()
                        Executors.newFixedThreadPool(4)
                )
        );

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(
                        this.getClass().getClassLoader()
                    )), new OioServerHandler());
            }
        });

        bootstrap.bind(new InetSocketAddress(8000));
    }

    private static class OioServerHandler extends SimpleChannelHandler {

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            Command command = (Command) e.getMessage();
            System.out.println(command.getActionName());
        }
    }
}
