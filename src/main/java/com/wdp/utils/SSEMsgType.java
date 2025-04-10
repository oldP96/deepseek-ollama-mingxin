package com.wdp.utils;

/**
 * @author wdp
 * @date 2025/3/26
 * @description 发送sse消息类型
 */
public enum SSEMsgType {

    MESSAGE("message","单次发送的普通消息"),
    ADD("add","追加消息，消息流式输出"),
    FINISH("finish","完成消息"),
    DONE("done","完成消息");
    public final String type;
    public final String desc;

    SSEMsgType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }


}
