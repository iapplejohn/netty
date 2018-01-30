/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SubReqClient.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 22:02
 * Description: 
 */
package com.jemmy.netty.guide.ch08;

import com.jemmy.netty.codec.protobuf.SubscribeReqProto;
import com.jemmy.netty.codec.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * <pre>
 * SubReqClient
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class SubReqClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new ProtobufVarint32FrameDecoder());
                     p.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                     p.addLast(new ProtobufVarint32LengthFieldPrepender());
                     p.addLast(new ProtobufEncoder());
                     p.addLast(new SubReqClientHandler());
                 }
             });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8988;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new SubReqClient().connect("localhost", port);
    }
}
