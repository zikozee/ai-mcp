package com.zee.systems.mcp;


import com.zee.systems.entity.Ticket;
import com.zee.systems.service.TicketService;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 23 Oct, 2026
 */

@Component
@RequiredArgsConstructor
public class TicketPromptProvider {

    private final TicketService ticketService;

    @McpPrompt(
            name = "analyze-tickets",
            description = "Instructions for analyzing zee systems support tickets")
    public McpSchema.GetPromptResult analyzeTicketsPrompt(){

        List<Ticket> tickets = ticketService.getAllTickets();

        StringBuilder ticketSummary = new StringBuilder();
        for(Ticket ticket : tickets){
            ticketSummary.append("Ticket ")
                    .append(ticket.getId()).append(": ").append(ticket.getTitle())
                    .append(" | Priority=").append(ticket.getPriority())
                    .append(" | Status=").append(ticket.getStatus())
                    .append("\n");
        }
        String prompt = """
                You are a ZeeSystems operations analyst.
                
                Analyze the current support tickets and provide insights.
                
                Tickets:
                """ + ticketSummary +"""
                
                Provide:
                1. Overall ticket situation
                2. High priority issues
                3. Possible operational risks
                4. Recommend next actions
                """;

        return McpSchema.GetPromptResult.builder(
                    Collections.singletonList(
                            McpSchema.PromptMessage.builder(
                                    McpSchema.Role.USER,
                                    McpSchema.TextContent.builder(prompt).build()
                            ).build()
                    )
                )
                .description("Analyze Tickets")
                .build();

    }
}
