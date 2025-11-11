package com.ka1yan.springai.zhipu.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/run")
    public String run() {
        return "hello world";
    }

    @GetMapping("/whoareu")
    public String whoAreU(@RequestParam(name = "query", defaultValue = "你是谁？") String query) {
        return chatModel.call(query);
    }

}
