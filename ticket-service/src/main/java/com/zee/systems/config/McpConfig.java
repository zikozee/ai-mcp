package com.zee.systems.config;


import com.zee.systems.mcp.TicketPromptProvider;
import io.modelcontextprotocol.server.McpStatelessServerFeatures;
import org.springframework.ai.mcp.annotation.spring.SyncMcpAnnotationProviders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 23 Oct, 2026
 */

@Configuration
public class McpConfig {

    @Bean
    List<McpStatelessServerFeatures.SyncPromptSpecification> ticketPrompts(TicketPromptProvider ticketPromptProvider) {
        return SyncMcpAnnotationProviders
                .statelessPromptSpecifications(Collections.singletonList(ticketPromptProvider));
    }
}
