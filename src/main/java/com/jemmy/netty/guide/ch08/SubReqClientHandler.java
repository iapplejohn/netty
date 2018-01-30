/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SubReqClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 22:06
 * Description: 
 */
package com.jemmy.netty.guide.ch08;

import com.jemmy.netty.codec.protobuf.SubscribeReqProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * SubReqClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }

        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("John");
        builder.setProductName("Netty book");
        List<String> addresses = new ArrayList<>();
        addresses.add("Hangzhou WestLake");
        addresses.add("Beijing LiuLiChang");
        addresses.add("Shenzhen HongShuLin");
        builder.addAllAddress(addresses);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
