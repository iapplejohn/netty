/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: ObjectClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/8 17:16
 * Description: 
 */
package com.jemmy.netty.coderli.ch08;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * ObjectClient
 *
 * @author Cheng Zhujiang
 * @date 2017/7/8
 */
public class ObjectClient {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        );

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                // return Channels.pipeline(new ObjectEncoder(),
                return Channels.pipeline(new MyObjEncoder(), // 自定义Object编码器
                        new ObjectClientHandler()); // 注意handler的顺序
            }
        });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class ObjectClientHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            sendObject(e.getChannel());
        }

        private void sendObject(Channel channel) {
            Command command = new Command();
            command.setActionName("Hello action");
            channel.write(command);
        }
    }
}
