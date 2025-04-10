package com.wdp.controller;

import com.wdp.utils.SSEMsgType;
import com.wdp.utils.SSEServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author wdp
 * @date 2025/3/26
 * @description sse api
 */
@Slf4j
@RestController
@RequestMapping("sse")
public class SSEController {

    // 连接SSE服务的接口
    @GetMapping(path = "connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter sseConnect(@RequestParam String userId) {
        return SSEServer.connect(userId);
    }

    // 发送单条消息给sse客户端
    @GetMapping("sendMessage")
    public Object sseSendMessage(@RequestParam String userId, @RequestParam String message) {
        SSEServer.sendMessage(userId, message, SSEMsgType.MESSAGE);
        return "OK";
    }

    // 发送消息给所有客户端
    @GetMapping("sendMessageAll")
    public Object sseSendMessageAll(@RequestParam String message) {
        SSEServer.sendMessageToAllUsers(message);
        return "OK";
    }

    // add事件流式输出
    @GetMapping("sendMessageAdd")
    public Object sseSendMessageAdd(@RequestParam String userId,
                                    @RequestParam String message) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200);
            SSEServer.sendMessage(userId, message + "-" + i, SSEMsgType.ADD);
        }
        return "OK";
    }

    // 停止sse
    @GetMapping("stop")
    public Object stopServer(@RequestParam String userId) {
        SSEServer.stopServer(userId);
        return "OK";
    }

    // 统计在线人数
    @GetMapping("getOnlineCount")
    public Object getOnlineCount() {
        return SSEServer.getOnlineCount();
    }
}
