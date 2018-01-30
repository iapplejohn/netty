/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MultiObjectServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/20 20:10
 * Description: 
 */
package com.jemmy.netty.coderli.ch10;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * MultiObjectServer
 *
 * @author Cheng Zhujiang
 * @date 2017/7/20
 */
public class MultiObjectServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(),
                    Executors.newCachedThreadPool()
        ));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(
                        this.getClass().getClassLoader()
                    )), new MultiObjectServerHandler());
            }
        });

        bootstrap.bind(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class MultiObjectServerHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            Command command = (Command) e.getMessage();
            System.out.println(command.getActionName());
        }
    }
}
