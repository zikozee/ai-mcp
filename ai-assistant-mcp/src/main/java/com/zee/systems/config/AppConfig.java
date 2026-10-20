package com.zee.systems.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @dev : Ezekiel Eromosei
 * @date : 20 Oct, 2026
 */

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    ChatClient openAIChatClient(ChatModel chatModel, SimpleLoggerAdvisor loggerAdvisor) {
        log.info("using 'OPENAI' chat client");
        return ChatClient.builder(chatModel).defaultAdvisors(loggerAdvisor).build();
    }

    @Bean
    SimpleLoggerAdvisor loggerAdvisor(){
        return new SimpleLoggerAdvisor();
    }
}
