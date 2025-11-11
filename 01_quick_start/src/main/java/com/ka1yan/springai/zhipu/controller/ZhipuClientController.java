package com.ka1yan.springai.zhipu.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.DefaultChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ZhipuClientController
 *
 * @author ka1yan
 * @date 2025/11/11 16:52
 * @description
 */
@RestController
@RequestMapping("/zhipu/client")
public class ZhipuClientController {

    private final ChatClient chatClient;

    public ZhipuClientController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 测试ChatClent进一步简化开发流程
     * <p>
     * ChatClent
     * </p>
     *
     * @param query 用户问题
     * @return 智能体流式响应
     */
    @GetMapping("/chatClient")
    public Flux<String> chatClient(@RequestParam(name = "query", defaultValue = "你是谁？") String query) {
        return chatClient.prompt()
                .system("你是一个智能问答助手，准确回答用户的问题。")
                .user(query)
                .options(new DefaultChatOptions())
                .stream()
                .content();
    }
}
