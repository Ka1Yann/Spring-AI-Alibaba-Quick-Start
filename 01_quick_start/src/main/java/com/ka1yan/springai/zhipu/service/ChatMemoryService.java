package com.ka1yan.springai.zhipu.service;

/**
 * ChatMemoryService
 *
 * @author ka1yan
 * @date 2025/11/11 22:03
 * @description
 */
public interface ChatMemoryService {

    /**
     * 测试自定义Advisor，进行消息的持久化，并实现每次从历史消息记录中获取最近5条对话记录，作为上下文使用。
     *
     * @param query     用户问题
     * @param conversationId 对话ID
     * @return 智能体流式响应
     */
    String chatWithMemory(String query, String conversationId);
}
