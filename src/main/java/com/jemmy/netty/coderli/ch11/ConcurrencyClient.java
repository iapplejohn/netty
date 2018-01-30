/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: ConcurrencyClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/24 16:42
 * Description: 
 */
package com.jemmy.netty.coderli.ch11;

import com.jemmy.netty.coderli.ch08.Command;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * ConcurrencyClient
 *
 * @author Cheng Zhujiang
 * @date 2017/7/24
 */
public class ConcurrencyClient {

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
                        new ConcurrencyNettyTestHander());
            }
        });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    private static class ConcurrencyNettyTestHander extends SimpleChannelHandler {

        private static int count = 0;

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            for (int i = 0; i < 1000; i++) { // 线程过多，导致系统假死
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendObject(e.getChannel());
                    }
                });
                System.out.println("Thread count: " + i);
                t.start();
            }
        }

        private void sendObject(Channel channel) {
            count++;
            Command command = new Command();
            command.setActionName("Hello action.");
            System.out.println("Write: " + count);
            channel.write(command);
        }
    }
}
