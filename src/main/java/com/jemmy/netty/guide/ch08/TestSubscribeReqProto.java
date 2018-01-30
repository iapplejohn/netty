/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TestSubscribeReqProto.java
 * Author:   Cheng Zhujiang
 * Date:     2017/12/3 20:31
 * Description: 
 */
package com.jemmy.netty.guide.ch08;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jemmy.netty.codec.protobuf.SubscribeReqProto;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * TestSubscribeReqProto
 *
 * @author Cheng Zhujiang
 * @date 2017/12/3
 */
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("John");
        builder.setProductName("Netty book");
        List<String> addresses = new ArrayList<>();
        addresses.add("Hangzhou WestLake");
        addresses.add("Beijing LiuLiChang");
        addresses.add("Shenzhen HongShuLin");
        builder.addAllAddress(addresses);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode : " + req2.toString());
        System.out.println("Assert equals ---> " + req2.equals(req));
    }
}
