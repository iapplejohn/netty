/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: BufferClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/5 22:18
 * Description: 
 */
package com.jemmy.netty.coderli.ch04;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * BufferClient
 *
 * @author Cheng Zhujiang
 * @date 2017/7/5
 */
public class BufferClient {

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
                return Channels.pipeline(new ClientBufferHandler());
            }
        });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class ClientBufferHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            // 分段发送信息
            sendMessageByFrame(e);
        }

        private void sendMessageByFrame(ChannelStateEvent e) {
            String msgOne = "Hello, ";
            String msgTwo = "I'm ";
            String msgThree = "client.";

            e.getChannel().write(tranStr2Buffer(msgOne));
            e.getChannel().write(tranStr2Buffer(msgTwo));
            e.getChannel().write(tranStr2Buffer(msgThree));
        }

        private Object tranStr2Buffer(String msg) {
            ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
            buffer.writeBytes(msg.getBytes());
            return buffer;
        }
    }
}
