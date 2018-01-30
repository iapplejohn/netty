/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: TestSelector.java
 * Author:   Cheng Zhujiang
 * Date:     2017/8/5 18:25
 * Description: 
 */
package com.jemmy.nio;

import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;

/**
 * TestSelector
 *
 * @author Cheng Zhujiang
 * @date 2017/8/5
 */
public class TestSelector {

    private static final int MAXSIZE = 5;

    public static void main(String[] args) {
        Selector[] selectors = new Selector[MAXSIZE];

        try {
            for (int i = 0; i < MAXSIZE; i++) {
                selectors[i] = Selector.open();
            }
            TimeUnit.MILLISECONDS.sleep(30000);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
