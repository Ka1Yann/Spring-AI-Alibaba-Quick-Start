package com.ka1yan.springai.zhipu.controller;

import com.ka1yan.springai.zhipu.service.ChatMemoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomAdvisorController
 *
 * @author ka1yan
 * @date 2025/11/11 21:18
 * @description 自定义Advisor
 */
@RestController
@RequestMapping("/custom/advisor")
public class CustomAdvisorController {

    private final ChatMemoryService chatMemoryService;

    public CustomAdvisorController(ChatMemoryService chatMemoryService) {
        this.chatMemoryService = chatMemoryService;
    }

    /**
     * 测试自定义Advisor，进行消息的持久化，并实现每次从历史消息记录中获取最近5条对话记录，作为上下文使用。
     *
     * @param query 用户问题
     * @param conversationId 对话ID
     * @return 智能体流式响应
     */
    @GetMapping("/chatWithMemory")
    public String chatWithMemory(@RequestParam(name = "query") String query, @RequestParam(name = "conversationId") String conversationId) {
        return chatMemoryService.chatWithMemory(query, conversationId);
    }

}
