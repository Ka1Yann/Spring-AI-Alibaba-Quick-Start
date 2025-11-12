package com.ka1yan.springai.zhipu.controller;

import com.ka1yan.springai.zhipu.entity.User;
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
    @GetMapping("/demo")
    public Flux<String> demo(@RequestParam(name = "query", defaultValue = "你是谁？") String query) {
        return chatClient.prompt()
                .system("你是一个智能问答助手，准确回答用户的问题。")
                .user(query)
                .options(new DefaultChatOptions())
                .stream()
                .content();
    }

    /**
     * 测试ChatClent-entity(); ChatClent直接返回实体对象
     * <p>
     * 原理：用户指定返回的内容后，Spring AI 会向 user prompt 后追加内容，使得 LLM 返回的内容必须为 Json 格式，并会指定 Json Schema，再由toEntity() 方法转化 Json 为实体类，从而实现调用 LLM 直接返回实体类。
     * </p>
     *
     * @return User 生成的用户实体
     */
    @GetMapping("/entity")
    public User LLM2Entity() {
        return chatClient.prompt()
                .system("你是一个智能问答助手，准确回答用户的问题。")
                .user("生成一个用户，并为他赋予各种合理的属性，要求使用中文。")
                .options(new DefaultChatOptions())
                .call()
                .entity(User.class);
    }

}
