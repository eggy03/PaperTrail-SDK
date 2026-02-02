package io.github.eggy03.papertrail.sdk.entity;

import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

@Value
@Builder(toBuilder = true)
public class AuditLogRegistrationEntity {

    @Nullable
    String guildId;

    @Nullable
    String channelId;
}