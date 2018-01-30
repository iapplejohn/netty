/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MsgpackEncoder.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/1 9:02
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * <pre>
 * MsgpackEncoder
 *
 * @author Cheng Zhujiang
 * @date 2017/12/1
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(msg);
        out.writeBytes(raw);
    }
}
