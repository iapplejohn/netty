/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: EchoClientHandler.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/1 9:35
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 * EchoClientHandler
 *
 * @author Cheng Zhujiang
 * @date 2017/12/1
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] infos = UserInfo();
        for (UserInfo info : infos) {
            ctx.write(info);
        }

        ctx.flush();
    }

    private UserInfo[] UserInfo() {
        UserInfo[] infos = new UserInfo[sendNumber];
        UserInfo info;
        for (int i = 0; i < sendNumber; i++) {
            info = new UserInfo();
            info.setAge(i);
            info.setName("ABCDEFG ---> " + i);
            infos[i] = info;
        }

        return infos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive msgpack message : " + msg);
        //ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
