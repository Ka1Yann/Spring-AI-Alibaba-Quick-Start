package com.ka1yan.springai.zhipu.mapper;

import com.ka1yan.springai.zhipu.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ChatMemoryDAO
 *
 * @author ka1yan
 * @date 2025/11/11 22:09
 * @description
 */
@Mapper
public interface ChatMemoryMapper {

    /**
     * 保存消息
     *
     * @param conversationId 会话ID
     * @param messages       消息列表
     * @param type
     */
    void saveMessages(@Param("conversationId") String conversationId, @Param("messages") List<String> messages, @Param("type") String type);

    /**
     * 保存消息
     *
     * @param conversationId 会话ID
     * @param message        消息内容
     * @param type
     */
    void saveMessage(@Param("conversationId") String conversationId, @Param("message") String message, @Param("type") String type);

    /**
     * 获取最近几条消息
     *
     * @param conversationId 会话ID
     * @param count 最大历史记录条数
     * @return 最近几条消息
     */
    List<ChatRecord> getLastMessages(@Param("conversationId") String conversationId, @Param("count") Integer count);

}
