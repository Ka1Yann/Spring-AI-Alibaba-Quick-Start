package com.ka1yan.springai.zhipu.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * PromptTemplateController
 *
 * @author ka1yan
 * @date 2025/11/12 11:18
 * @description
 */
@RestController
@RequestMapping("/prompt/template")
public class PromptTemplateController {

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 测试PromptTemplate
     *
     * @param location 地点
     * @return 智能体流式响应
     */
    @RequestMapping("/test")
    public String test(String location) {
        PromptTemplate promptTemplate = new PromptTemplate("帮我查一下{{location}}有什么好玩的？");
        Message userMessage = promptTemplate.createMessage(Map.of("location", location));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是一个{{role}},你的名字是{{name}}");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("role", "人工智能小助手", "name", "小白"));
        return chatClient.prompt().messages(systemMessage, userMessage).call().content();
    }

    public static void main(String[] args) {
        PromptTemplate promptTemplate = new PromptTemplate("帮我查一下{location}有什么好玩的？");
        Message userMessage = promptTemplate.createMessage(Map.of("location", "北京"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是一个{role},你的名字是{name}");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("role", "人工智能小助手", "name", "小白"));

        System.out.println(systemMessage);
        System.out.println(userMessage);

        PromptTemplate template = new PromptTemplate("Hello {{name}}");
        String result = template.render(Map.of("name", "测试"));
        System.out.println(result);

    }

}
