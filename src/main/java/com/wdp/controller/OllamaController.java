package com.wdp.controller;

import com.wdp.bean.ChatBean;
import com.wdp.service.ChatRecordService;
import com.wdp.service.OllamaService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wdp
 * @date 2025/3/25
 */

@Slf4j
@RestController
@RequestMapping("ollama")
public class OllamaController {

    @Resource
    private OllamaService ollamaService;

    @Resource
    private ChatRecordService chatRecordService;

    @GetMapping("ai/chat")
    public Object aiOllamaChat(@RequestParam String msg) {
        return ollamaService.aiOllamaChat(msg);
    }

    @GetMapping("ai/stream")
    public List<String> aiOllamaStream(@RequestParam String msg) {
        return ollamaService.aiOllamaStream(msg);
    }

    @PostMapping("ai/mingxin")
    public void aiOllamaMingxin(@RequestBody ChatBean chatBean) {
        log.info(chatBean.toString());
        String currentUserName = chatBean.getCurrentUserName();
        String msg = chatBean.getMessage();
        ollamaService.aiOllamaMingxin(currentUserName, msg);
    }

    @GetMapping("ai/getRecords")
    public Object getChatRecordList(@RequestParam String who) {
        return chatRecordService.getChatRecordList(who);
    }

}
