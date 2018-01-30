/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: OioClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/5 16:17
 * Description: 
 */
package com.jemmy.netty.coderli.ch14;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * OioClient
 *
 * @author Cheng Zhujiang
 * @date 2017/8/5
 */
public class OioClient {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap(
                new OioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        null
                )
        );

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectEncoder(),
                        new OioClientHandler());
            }
        });

        bootstrap.connect(new InetSocketAddress(8000));
    }

    private static class OioClientHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            sendObject(e.getChannel());
        }

        private void sendObject(Channel channel) {
            Command command = new Command();
            command.setActionName("Hello OioServer");
            channel.write(command);
        }
    }
}
