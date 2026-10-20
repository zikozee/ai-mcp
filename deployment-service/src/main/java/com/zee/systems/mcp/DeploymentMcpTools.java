package com.zee.systems.mcp;


import com.zee.systems.datatype.DeploymentEnvironment;
import com.zee.systems.entity.Deployment;
import com.zee.systems.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 20 Oct, 2026
 */

@Component
@RequiredArgsConstructor
public class DeploymentMcpTools {

    private final DeploymentService deploymentService;

    @McpTool(name = "getDeployments",
            description = "Get all deployments",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = true, destructiveHint = false,
                    idempotentHint = true, openWorldHint = false)
    )
    public List<Deployment> getDeployments(){
        return deploymentService.getDeployments();
    }



    @McpTool(name = "getDeployment",
            description = "Get a deployment by it's id",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = true, destructiveHint = false,
                    idempotentHint = true, openWorldHint = false)
    )
    public Deployment getDeployment(@McpToolParam(description = "The id of the deployment") Long id){
        return deploymentService.getDeployment(id);
    }


//    @McpTool(name = "createDeployment",
//            description = "Create a new deployment. Provide serviceName, environment(DEV, QA, PROD), scheduledTime ISO datetime (yyyy-MM-ddTHH:mm:ss), owner",
//            annotations = @McpTool.McpAnnotations(
//                    readOnlyHint = false, destructiveHint = false,
//                    idempotentHint = false, openWorldHint = false)
//    )
//    public Deployment createDeployment(@McpToolParam(description = "The service name") String serviceName,
//                                       @McpToolParam(description = "the deployment environment, i.e DEV, QA or PROD")String environment,
//                                       @McpToolParam(description = "The scheduled time of deployment")String scheduledTime,
//                                       @McpToolParam(description = "who owns the deployment")String owner){
//        return deploymentService.createDeployment(
//                serviceName,
//                DeploymentEnvironment.valueOf(environment),
//                LocalDateTime.parse(scheduledTime),
//                owner
//        );
//    }

    @McpTool(name = "createDeployment",
            description = "Create a new deployment. Provide serviceName, environment(DEV, QA, PROD), scheduledTime ISO datetime (yyyy-MM-ddTHH:mm:ss), owner",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = false, openWorldHint = false)
    )
    public Deployment createDeployment(@McpToolParam(description = "The deployment details") CreateDeploymentDto createDeploymentDto){
        return deploymentService.createDeployment(
                createDeploymentDto.serviceName(),
                createDeploymentDto.environment(),
                createDeploymentDto.scheduledTime(),
                createDeploymentDto.owner()
        );
    }



    @McpTool(name = "assignOwner",
            description = "Assign a new owner to an existing deployment",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = false, openWorldHint = false)
    )
    public Deployment assignOwner(@McpToolParam(description = "The id of the deployment") Long id,
                                  @McpToolParam(description = "The new Owner") String newOwner){
        return deploymentService.assignOwner(id, newOwner);
    }


    @McpTool(name = "rescheduleDeployment",
            description = "Reschedule a deployment to a new ISO datetime (yyyy-MM-ddTHH:mm:ss)",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = false, openWorldHint = false)
    )
    public Deployment rescheduleDeployment(@McpToolParam(description = "The id of the deployment") Long id,
                                  @McpToolParam(description = "The new deployment time in ISO datetime (yyyy-MM-ddTHH:mm:ss)") String newTime){
        return deploymentService.rescheduleDeployment(id, LocalDateTime.parse(newTime));
    }



    @McpTool(name = "cancelDeployment",
            description = "Cancel a deployment by it's id",
            annotations = @McpTool.McpAnnotations(
                    readOnlyHint = false, destructiveHint = false,
                    idempotentHint = true, openWorldHint = false)
    )
    public Deployment cancelDeployment(@McpToolParam(description = "The id of the deployment") Long id){
        return deploymentService.cancelDeployment(id);
    }

}
