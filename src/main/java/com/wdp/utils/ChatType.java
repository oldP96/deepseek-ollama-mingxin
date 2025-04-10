package com.wdp.utils;

/**
 * @author wdp
 * @date 2025/3/31
 * @description chat type enum
 */
public enum ChatType {
    USER("user","用户发送的内容"),
    BOT("bot","AI的回复内容");

    public final String type;
    public final String desc;

    ChatType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
