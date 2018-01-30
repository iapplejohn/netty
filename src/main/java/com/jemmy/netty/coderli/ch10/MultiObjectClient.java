/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MultiObjectClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/20 20:19
 * Description: 
 */
package com.jemmy.netty.coderli.ch10;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * MultiObjectClient
 *
 * @author Cheng Zhujiang
 * @date 2017/7/20
 */
public class MultiObjectClient {

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
                return Channels.pipeline(new ObjectEncoder(),
                        new MultiObjectClientHandler()
                );
            }
        });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class MultiObjectClientHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            sendObject(e.getChannel());
        }

        private void sendObject(Channel channel) {
            Command command = new Command();
            command.setActionName("Hello action.");
            Command commandOne = new Command();
            commandOne.setActionName("Hello action. one");
            Command commandTwo = new Command();
            commandTwo.setActionName("Hello action. two");
            channel.write(commandTwo);
            channel.write(command);
            channel.write(commandOne);
        }
    }
}
