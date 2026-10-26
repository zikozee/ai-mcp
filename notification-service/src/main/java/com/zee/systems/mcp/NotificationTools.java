package com.zee.systems.mcp;


import com.zee.systems.datatype.NotificationChannel;
import com.zee.systems.entity.Notification;
import com.zee.systems.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

/**
 * @dev : Ezekiel Eromosei
 * @date : 23 Oct, 2026
 */

@Component
@RequiredArgsConstructor
public class NotificationTools {

    private final NotificationService notificationService;


    @McpTool(name = "sendNotification",
            description = "Send a notification to a team using the specified channel",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = false, openWorldHint = false
            )
    )
    public Notification sendNotification(
            @McpToolParam(description = "The notification channel") NotificationChannel channel,
            @McpToolParam(description = "The recipient team") String recipient,
            @McpToolParam(description = "Notification message") String message) {
        return notificationService
                .sendNotification(channel, recipient, message);
    }


}
