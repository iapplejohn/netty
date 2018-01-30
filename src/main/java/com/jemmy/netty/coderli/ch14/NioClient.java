/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: NioClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/5 15:08
 * Description: 
 */
package com.jemmy.netty.coderli.ch14;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * NioClient
 *
 * @author Cheng Zhujiang
 * @date 2017/8/5
 */
public class NioClient {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newFixedThreadPool(4)
                )
        );

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectEncoder(),
                        new NioClientHandler());
            }
        });

        bootstrap.connect(new InetSocketAddress(8000));
    }

    private static class NioClientHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            sendObject(e.getChannel());
        }

        private void sendObject(Channel channel) {
            Command command = new Command();
            command.setActionName("Hello NioServer");
            channel.write(command);
        }
    }
}
