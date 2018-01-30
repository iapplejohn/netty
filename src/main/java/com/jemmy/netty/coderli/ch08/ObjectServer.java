/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: ObjectServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/8 17:06
 * Description: 
 */
package com.jemmy.netty.coderli.ch08;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * ObjectServer
 *
 * @author Cheng Zhujiang
 * @date 2017/7/8
 */
public class ObjectServer {

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
                return Channels.pipeline(
//                        new ObjectDecoder(ClassResolvers.cacheDisabled(
//                                this.getClass().getClassLoader()
//                        )), new ObjectServerHandler()
                        new MyObjDecoder(), new ObjectServerHandler()
                );

                // 注册到Server的Handler是有顺序的，如果你颠倒一下注册顺序
//                return Channels.pipeline(
//                        new ObjectServerHandler(), new ObjectDecoder(ClassResolvers.cacheDisabled(
//                                this.getClass().getClassLoader()
//                        ))
//                );
            }
        });

        bootstrap.bind(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class ObjectServerHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            Command command = (Command) e.getMessage();
            System.out.println(command.getActionName());
        }
    }
}
