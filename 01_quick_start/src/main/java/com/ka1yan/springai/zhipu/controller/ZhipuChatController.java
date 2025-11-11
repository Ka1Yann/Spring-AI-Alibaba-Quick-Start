package com.ka1yan.springai.zhipu.controller;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * ZhipuChatController
 *
 * @author ka1yan
 * @date 2025/11/11 14:29
 * @description
 */
@RestController
@RequestMapping("/zhipu/chat")
public class ZhipuChatController {

    private final ChatModel chatModel;

    public ZhipuChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 测试
     *
     * @return hello world
     */
    @GetMapping("/run")
    public String run() {
        return "hello world";
    }

    /**
     * 测试ai响应
     *
     * @param query 用户问题
     * @return 智能体回答
     */
    @GetMapping("/whoareu")
    public String whoAreU(@RequestParam(name = "query", defaultValue = "你是谁？") String query) {
        return chatModel.call(query);
    }

    /**
     * 测试flux流式响应
     * <p>
     *     chatModel.call() / prompt / messages / chatOptions
     * </p>
     *
     * @param query 用户问题
     * @return 智能体流式响应
     */
    @GetMapping("/flux")
    public Flux<String> flux(@RequestParam(name = "query", defaultValue = "你是谁？") String query) {
        SystemMessage systemMessage = new SystemMessage("你是一个智能问答助手，准确回答用户的问题。");
        UserMessage userMessage = new UserMessage(query);
        return chatModel.stream(
                new Prompt(List.of(systemMessage, userMessage)
                        , ZhiPuAiChatOptions.builder()
                        .temperature(0.0)
                        .maxTokens(65535)
                        .build())).mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }


}
