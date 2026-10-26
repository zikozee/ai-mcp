package com.zee.systems.service;


import com.zee.systems.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;


/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;
    private final ToolCallbackProvider mcpToolProvider;
    private final PromptLoader promptLoader;

    public ChatResponseDto handleMessage(String message){

        String processedPrompt = promptLoader.loadPrompt(message);

        // in real world this would come from our security context
        String currentUser = "john.doe";
        String currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toString();

        String systemPrompt = """
                You are an enterprise AI assistant.
                Context:
                - Current user: %s
                - Current date and time: %s
                """.formatted(currentUser, currentTime);

        String content = chatClient.prompt()
                .system(systemPrompt)
                .user(processedPrompt)
                .tools(mcpToolProvider)
                .call()
                .content();


        return new ChatResponseDto(content);
    }
}
