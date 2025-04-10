package com.wdp.bean;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wdp
 * @date 2025/3/31
 * @description table
 */

@Data
@ToString
public class ChatRecord {

    private String id;
    private String content;
    private String chatType;
    private LocalDateTime chatTime;
    private String memberName;
}
