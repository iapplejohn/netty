/*
 * Copyright (C), 2014-2017, 杭州小卡科技有限公司
 * FileName: Command.java
 * Author:   Cheng Zhujiang
 * Date:     2017/7/8 17:14
 * Description: 
 */
package com.jemmy.netty.coderli.ch08;

import java.io.Serializable;

/**
 * Command
 *
 * @author Cheng Zhujiang
 * @date 2017/7/8
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 7590999461767050471L;

    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
