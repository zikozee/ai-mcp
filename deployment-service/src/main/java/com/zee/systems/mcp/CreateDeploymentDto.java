package com.zee.systems.mcp;



import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.zee.systems.datatype.DeploymentEnvironment;

import java.time.LocalDateTime;

/**
 * @dev : Ezekiel Eromosei
 * @date : 20 Oct, 2026
 */

public record CreateDeploymentDto(
        @JsonPropertyDescription("The service name")
        String serviceName,
        @JsonPropertyDescription("The deployment environment, i.e DEV, QA or PROD")
        DeploymentEnvironment environment,
        @JsonPropertyDescription("The scheduled time of deployment in ISO datetime (yyyy-MM-ddTHH:mm:ss)")
        LocalDateTime scheduledTime,
        @JsonPropertyDescription("The owner of the deployment")
        String owner
) {
}
