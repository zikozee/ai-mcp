package com.zee.systems.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

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
