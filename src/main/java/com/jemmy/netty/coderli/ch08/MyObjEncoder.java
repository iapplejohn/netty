/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MyObjEncoder.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/20 19:49
 * Description: 
 */
package com.jemmy.netty.coderli.ch08;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 自定义Object编码类
 *
 * @author Cheng Zhujiang
 * @date 2017/7/20
 */
public class MyObjEncoder implements ChannelDownstreamHandler {

    @Override
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) { // 处理收发信息的情形
            MessageEvent mEvent = (MessageEvent) e;
            Object obj = mEvent.getMessage();
            if (!(obj instanceof Command)) {
                ctx.sendDownstream(e);
                return;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
            buffer.writeBytes(out.toByteArray());
            e.getChannel().write(buffer);
        } else { // 其他事件，自动流转。比如，bind，connected
            ctx.sendDownstream(e);
        }
    }
}
