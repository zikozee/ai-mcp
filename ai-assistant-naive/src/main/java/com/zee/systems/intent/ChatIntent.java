package com.zee.systems.intent;


import lombok.Builder;

/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@Builder
public record ChatIntent(
        IntentType intent,
        String username,
        String date, // yyyy-MM-dd
        String deploymentId
) {}
