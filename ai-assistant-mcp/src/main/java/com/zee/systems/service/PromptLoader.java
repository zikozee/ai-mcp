package com.zee.systems.service;


import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 23 Oct, 2026
 */

@Service
@RequiredArgsConstructor
public class PromptLoader {

    private final List<McpSyncClient> mcpSyncClientList;


    public String loadPrompt(String message){
        if(!message.startsWith("/")){
            return message;
        }

        String promptName = message.substring(1);

        for (McpSyncClient mcpSyncClient : mcpSyncClientList) {
            List<McpSchema.Prompt> prompts = mcpSyncClient.listPrompts().prompts();

            boolean exists = prompts.stream()
                    .anyMatch(prompt -> prompt.name().equalsIgnoreCase(promptName));

            if(exists){
                McpSchema.GetPromptResult response = mcpSyncClient.getPrompt(McpSchema.GetPromptRequest.builder(promptName).build());
                McpSchema.TextContent content = (McpSchema.TextContent) response.messages().getFirst().content();
                return content.text();
            }
        }
        return message;
    }
}
