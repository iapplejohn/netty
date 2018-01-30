/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: MsgPackTest.java
 * Author:   Cheng Zhujiang
 * Date:     2017/11/30 20:17
 * Description: 
 */
package com.jemmy.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * MsgPackTest
 *
 * @author Cheng Zhujiang
 * @date 2017/11/30
 */
public class MsgPackTest {

    public static void main(String[] args) throws IOException {
        // Create serialize objects.
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");

        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(src);

        // Deserialize directly using a template
        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dst1.get(0));
        System.out.println(dst1.get(1));
        System.out.println(dst1.get(2));
    }

}
