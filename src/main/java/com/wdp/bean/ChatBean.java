package com.wdp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wdp
 * @date 2025/3/28
 * @description chatbean
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatBean {
    private String currentUserName;
    private String message;
}
