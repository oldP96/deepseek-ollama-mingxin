package com.wdp.service;

import java.util.List;

/**
 * @author wdp
 * @date 2025/3/25
 */
public interface OllamaService {

    Object aiOllamaChat(String msg);

    List<String> aiOllamaStream(String msg);

    void aiOllamaMingxin(String username,String msg);
}
