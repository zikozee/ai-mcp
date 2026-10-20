package com.zee.systems.mcp;


import com.zee.systems.entity.LeaveRecord;
import com.zee.systems.service.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @dev : Ezekiel Eromosei
 * @date : 20 Oct, 2026
 */

@Component
@RequiredArgsConstructor
public class HrMcpTools {

    private final HrService hrService;

    @McpTool(name = "applyLeave",
            description = "Apply leave for a user on a specific ISO-8601 date (yyyy-MM-dd)",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = false, openWorldHint = false)
    )
    public String applyLeave(@McpToolParam(description = "The user name") String username,
                             @McpToolParam(description = "The date to apply leave om") String date){

        LeaveRecord leaveRecord = hrService.applyLeave(username, LocalDate.parse(date));
        return "Leave applied for " + leaveRecord.getUsername() + " on " + leaveRecord.getLeaveDate();
    }



    @McpTool(name = "findReplacement",
            description = "Find a replacement employee for a user on a specific date - ISO-8601 date format (yyyy-MM-dd)",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = true, destructiveHint = false,
                    idempotentHint = true, openWorldHint = false)
    )
    public String findReplacement(@McpToolParam(description = "The user name") String username,
                             @McpToolParam(description = "The date to apply leave on") String date){

        return hrService.findReplacement(username, LocalDate.parse(date));
    }
}
