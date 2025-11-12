package com.ka1yan.springai.zhipu.service.impl;

import com.ka1yan.springai.zhipu.advisor.CustomChatMemoryAdvisor;
import com.ka1yan.springai.zhipu.mapper.ChatMemoryMapper;
import com.ka1yan.springai.zhipu.service.ChatMemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

/**
 * ChatMemoryServiceImpl
 *
 * @author ka1yan
 * @date 2025/11/11 22:06
 * @description
 */
@Service
public class ChatMemoryServiceImpl implements ChatMemoryService {

    private final ChatClient chatClient;
    private final ChatMemoryMapper chatMemoryMapper;

    public ChatMemoryServiceImpl(ChatClient.Builder builder, ChatMemoryMapper chatMemoryMapper) {
        this.chatClient = builder.build();
        this.chatMemoryMapper = chatMemoryMapper;
    }

    /**
     * 测试自定义Advisor，进行消息的持久化，并实现每次从历史消息记录中获取最近5条对话记录，作为上下文使用。
     *
     * @param query     用户问题
     * @param conversationId 对话ID
     * @return 智能体流式响应
     */
    @Override
    public String chatWithMemory(String query, String conversationId) {
        return chatClient.prompt()
                .user(query)
                .system("你是一个智能的AI问答助手，请耐心思考并认真回答用户问题！")
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId)) // consumer to mark the conversation id
                .advisors(new CustomChatMemoryAdvisor(chatMemoryMapper)) // memory
                .call().content();
    }
}
