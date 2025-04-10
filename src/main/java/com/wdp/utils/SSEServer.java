package com.wdp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author wdp
 * @date 2025/3/26
 * @description sse服务
 */
@Slf4j
public class SSEServer {

    // 使用map对象，关联用户id和sse的服务连接
    private static final Map<String, SseEmitter> sseClients = new ConcurrentHashMap<>();

    private static AtomicInteger onlineCounts = new AtomicInteger(0);

    public static SseEmitter connect(String userId) {
        // 设置超时时间，0L代表永不过期，默认30秒，超时未完成任务则会抛出异常
        SseEmitter sseEmitter = new SseEmitter(0L);

        // 注册SSE的回调方法
        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onError(errorCallback(userId));
        sseEmitter.onTimeout(timeoutCallback(userId));

        sseClients.put(userId, sseEmitter);
        log.info("当前创建新的SSE连接，用户id为：{}", userId);
        onlineCounts.getAndIncrement();
        return sseEmitter;
    }

    // 发送单条消息
    public static void sendMessage(String userId, String message, SSEMsgType msgType) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }
        if (sseClients.containsKey(userId)) {
            SseEmitter sseEmitter = sseClients.get(userId);
            sendEmitterMessage(userId, sseEmitter, message, msgType);
        }
    }

    // 发送消息给所有用户
    public static void sendMessageToAllUsers(String message) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }
        sseClients.forEach((userId, sseEmitter) -> {
            sendEmitterMessage(userId, sseEmitter, message, SSEMsgType.MESSAGE);
        });
    }

    public static void sendEmitterMessage(String userId,
                                          SseEmitter sseEmitter,
                                          String message,
                                          SSEMsgType msgType) {
        SseEmitter.SseEventBuilder msg = SseEmitter.event().id(userId)
                .name(msgType.type)
                .data(message);
        try {
            sseEmitter.send(msg);
        } catch (IOException e) {
            log.error("用户[{}]的消息推送发生异常", userId);
            removeConnection(userId);
        }
    }

    // 主动切断，停止sse和客户端的连接
    public static void stopServer(String userId) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }
        SseEmitter sseEmitter = sseClients.get(userId);
        if (sseEmitter != null) {
            // complete表示执行完毕，断开连接
            sseEmitter.complete();
            removeConnection(userId);
            log.info("连接关闭成功，被关闭的用户为{}", userId);
        } else {
            log.warn("当前连接无需关闭，请不要重复操作");
        }
    }


    // sse连接完成后的回调方法(关闭连接的时候调用)
    private static Runnable completionCallback(String userId) {
        return () -> {
            log.info("SSE连接完成并结束，用户id为：{}", userId);
            removeConnection(userId);
        };
    }

    // sse连接超时后的回调方法
    private static Runnable timeoutCallback(String userId) {
        return () -> {
            log.info("SSE连接超时，用户id为：{}", userId);
            removeConnection(userId);
        };
    }

    // sse连接发生错误后的回调方法
    private static Consumer<Throwable> errorCallback(String userId) {
        return T -> {
            log.info("SSE连接发生异常，用户id为：{}", userId);
            removeConnection(userId);
        };
    }


    public static void removeConnection(String userId) {
        sseClients.remove(userId);
        log.info("SSE连接被移除，移除的用户id为：{}", userId);
        onlineCounts.getAndDecrement();
    }

    // 在线人数
    public static int getOnlineCount() {
        return onlineCounts.intValue();
    }
}
