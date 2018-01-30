/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: SpdyFrameLogger.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/2 11:28
 * Description: 
 */
package com.jemmy.netty.v4.spdy.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * <pre>
 * SpdyFrameLogger
 *
 * @author Cheng Zhujiang
 * @date 2017/12/2
 */
public class SpdyFrameLogger extends ChannelDuplexHandler {

    protected final InternalLogger logger;
    private final InternalLogLevel level;

    public SpdyFrameLogger(InternalLogLevel level) {
        if (level == null) {
            throw new NullPointerException("level");
        }

        logger = InternalLoggerFactory.getInstance(getClass());
        this.level = level;
    }
}
