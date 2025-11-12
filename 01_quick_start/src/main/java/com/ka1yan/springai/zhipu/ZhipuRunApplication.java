package com.ka1yan.springai.zhipu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ZhipuRunApplication
 *
 * @author ka1yan
 * @date 2025/11/11 14:31
 * @description
 */
@SpringBootApplication
@MapperScan("com.ka1yan.springai.zhipu.mapper")
public class ZhipuRunApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhipuRunApplication.class, args);
    }

}
