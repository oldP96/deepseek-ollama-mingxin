package com.wdp.service.impl;

import com.wdp.service.ChatRecordService;
import com.wdp.service.OllamaService;
import com.wdp.utils.ChatType;
import com.wdp.utils.SSEMsgType;
import com.wdp.utils.SSEServer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wdp
 * @date 2025/3/25
 */
@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    @Resource
    private OllamaChatClient ollamaChatClient;

    @Resource
    private ChatRecordService chatRecordService;

    @Override
    public Object aiOllamaChat(String msg) {
        return ollamaChatClient.call(msg);
    }

    @Override
    public List<String> aiOllamaStream(String msg) {
        Prompt prompt = new Prompt(new UserMessage(msg));
        Flux<ChatResponse> responseFlux = ollamaChatClient.stream(prompt);
        List<String> list;
        list = responseFlux.toStream().map(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            log.info(content);
            return content;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public void aiOllamaDoctor(String username, String msg) {
        // 保存用户发送的消息
        chatRecordService.saveChatRecord(username, msg, ChatType.USER);
        Prompt prompt = new Prompt(new UserMessage(msg));
        Flux<ChatResponse> responseFlux = ollamaChatClient.stream(prompt);
        List<String> list = responseFlux.toStream().map(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            SSEServer.sendMessage(username, content, SSEMsgType.ADD);
            log.info(content);
            return content;
        }).toList();
        SSEServer.sendMessage(username, "DONE", SSEMsgType.FINISH);
        // 保存AI的回复消息
        StringBuilder html = new StringBuilder();
        for (String s : list) {
            html.append(s);
        }
        chatRecordService.saveChatRecord(username, html.toString(), ChatType.BOT);
    }
}
