/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SubReqServerHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 21:52
 * Description: 
 */
package com.jemmy.netty.guide.ch08;

import com.jemmy.netty.codec.protobuf.SubscribeReqProto;
import com.jemmy.netty.codec.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 * SubReqServerHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("John".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Server accepts client subscribe req : [" + msg + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private Object resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
