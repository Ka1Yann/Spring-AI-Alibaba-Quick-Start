package com.ka1yan.springai.zhipu.advisor;

import com.ka1yan.springai.zhipu.entity.ChatRecord;
import com.ka1yan.springai.zhipu.mapper.ChatMemoryMapper;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Content;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomChatMemoryAdvisor
 *
 * @author ka1yan
 * @date 2025/11/11 21:27
 * @description 实现基本的记忆功能，默认取五条数据，可以根据情况具体配置
 */
@Component
public class CustomChatMemoryAdvisor implements BaseChatMemoryAdvisor {

    /**
     * 最大历史记录条数
     */
    private static final Integer maxHistorySize = 5;

    private final ChatMemoryMapper chatMemoryMapper;

    public CustomChatMemoryAdvisor(ChatMemoryMapper chatMemoryMapper) {
        this.chatMemoryMapper = chatMemoryMapper;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        // 获取当前请求的用户信息
        List<Message> requestMessages = chatClientRequest.prompt().getInstructions();
        List<Message> userMessages = requestMessages.stream().filter(message -> message.getMessageType().equals(MessageType.USER)).toList();
        Assert.notNull(userMessages, "userMessages cannot be null");

        // 获取历史信息最近五条
        String conversationId = chatClientRequest.context().get(ChatMemory.CONVERSATION_ID).toString();
        List<ChatRecord> historyRecords = chatMemoryMapper.getLastMessages(conversationId, maxHistorySize);

        // 将历史信息转化为Message,添加到messages中,并添加本次消息到message中
        List<Message> messages = new ArrayList<>();
//        historyRecords.forEach(record -> messages.add(new UserMessage(record)));
        for (ChatRecord record : historyRecords) {
            if (record == null || record.getType() == null) {
                continue;
            } else if (record.getType().equals(MessageType.USER.getValue())) {
                messages.add(new UserMessage(record.getMessage()));
            } else {
                messages.add(new UserMessage(record.getMessage()));
            }
        }
        messages.addAll(requestMessages);

        // 对本次信息进行持久化
        chatMemoryMapper.saveMessages(conversationId, userMessages.stream().map(Content::getText).toList(), MessageType.USER.getValue());

        // 将历史信息中的最近五条一并发送给LLM
        return chatClientRequest.mutate()
                .prompt(new Prompt(messages))
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        // 获取LLM返回信息
        Assert.notNull(chatClientResponse.chatResponse(), "chatResponse cannot be null");
        String assistantMessage = chatClientResponse.chatResponse().getResult().getOutput().getText();
        String conversationId = chatClientResponse.context().get(ChatMemory.CONVERSATION_ID).toString();

        // 将LLM返回信息进行持久化
        chatMemoryMapper.saveMessage(conversationId, assistantMessage, MessageType.ASSISTANT.getValue());
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
