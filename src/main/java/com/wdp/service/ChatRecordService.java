package com.wdp.service;

import com.wdp.bean.ChatRecord;
import com.wdp.utils.ChatType;

import java.util.List;

/**
 * @author wdp
 * @date 2025/3/31
 * @description ChatRecordService
 */
public interface ChatRecordService {

    // 保存用户AI的聊天记录
    void saveChatRecord(String username, String message, ChatType chatType);

    // 获取历史用户AI的聊天记录
    List<ChatRecord> getChatRecordList(String who);
}
