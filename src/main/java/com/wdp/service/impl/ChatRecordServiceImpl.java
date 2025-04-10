package com.wdp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdp.bean.ChatRecord;
import com.wdp.mapper.ChatRecordMapper;
import com.wdp.service.ChatRecordService;
import com.wdp.utils.ChatType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wdp
 * @date 2025/3/31
 * @description ChatRecordServiceImpl
 */
@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    public void saveChatRecord(String username, String message, ChatType chatType) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setMemberName(username);
        chatRecord.setContent(message);
        chatRecord.setChatType(chatType.type);
        chatRecord.setChatTime(LocalDateTime.now());
        chatRecordMapper.insert(chatRecord);
    }

    @Override
    public List<ChatRecord> getChatRecordList(String who) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_name", who);
        return chatRecordMapper.selectList(queryWrapper);
    }
}
