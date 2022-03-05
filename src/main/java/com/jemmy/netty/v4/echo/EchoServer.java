/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoServer.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/18 19:35
 * Description: 
 */
package com.jemmy.netty.v4.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * <pre>
 * EchoServer
 *
 * @author Cheng Zhujiang
 * @date 2017/11/18
 */
public class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8009"));

    public static void main(String[] args) throws CertificateException, SSLException, InterruptedException {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // 1. bossGroup 用于接收连接，workerGroup 用于具体的处理
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 创建服务器启动引导/辅助类: ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            // 3. 给引导类配置两大线程组，确定了线程模型
            b.group(bossGroup, workerGroup)
                // 4. 指定 IO 模型
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 128)
                //（非必备）打印日志
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     // 5. 可以自定义客户端消息的业务处理逻辑
                     p.addLast(new EchoServerHandler());
                 }
             });

            // Start the server. 6. 绑定端口，调用 sync 方法阻塞直到绑定完成
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed. // 7. 阻塞等待直到服务器Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads. 8. 优雅关闭相关线程组资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
