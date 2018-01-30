/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: QuoteOfTheMomentServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/27 9:21
 * Description: 
 */
package com.jemmy.netty.v4.qotm;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * <pre>
 * QuoteOfTheMomentServerHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/11/27
 */
public class QuoteOfTheMomentServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Random random = new Random();

    private static final String[] quotes = {
        "Where there is love there is life.",
        "First they ignore you, then they laugh at you, then they fight you, then you win.",
        "Be the change you want to see in the world.",
        "The weak can never forgive. Forgiveness is the attribute of the strong.",
    };

    private static String nextQuote() {
        int quoteId;

        synchronized (random) {
            quoteId = random.nextInt(quotes.length);
        }
        return quotes[quoteId];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        System.err.println(packet);
        if ("QOTM?".equals(packet.content().toString(CharsetUtil.UTF_8))) {
            ctx.write(new DatagramPacket(
                    Unpooled.copiedBuffer("QOTM: " + nextQuote(), CharsetUtil.UTF_8), packet.
                    sender()
            ));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }
}
