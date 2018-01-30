/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MyObjDecoder.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/20 19:57
 * Description: 
 */
package com.jemmy.netty.coderli.ch08;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * 自定义Object解码类
 *
 * @author Cheng Zhujiang
 * @date 2017/7/20
 */
public class MyObjDecoder implements ChannelUpstreamHandler {

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) {
            MessageEvent mEvent = (MessageEvent) e;
            if (!(mEvent.getMessage() instanceof ChannelBuffer)) {
                ctx.sendUpstream(mEvent);
                return;
            }

            ChannelBuffer buffer = (ChannelBuffer) mEvent.getMessage();
            ByteArrayInputStream input = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(input);
            Object obj = ois.readObject();
            Channels.fireMessageReceived(e.getChannel(), obj);
        }

    }
}
