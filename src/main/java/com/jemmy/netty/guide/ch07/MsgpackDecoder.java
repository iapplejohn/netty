/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MsgpackDecoder.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/1 9:08
 * Description: 
 */
package com.jemmy.netty.guide.ch07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * <pre>
 * MsgpackDecoder
 *
 * @author Cheng Zhujiang
 * @date 2017/12/1
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        int length = msg.readableBytes();
        array = new byte[length];
        msg.getBytes(msg.readerIndex(), array, 0, length);
        MessagePack msgpack = new MessagePack();
        out.add(msgpack.read(array));
    }
}
