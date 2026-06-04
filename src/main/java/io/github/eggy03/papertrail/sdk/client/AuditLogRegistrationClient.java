package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.AuditLogRegistrationEntity;
import io.github.eggy03.papertrail.sdk.service.AuditLogRegistrationService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Client for managing audit log registrations via the PaperTrail API.
 */
@SuppressWarnings("java:S1192")
public final class AuditLogRegistrationClient {

    private static final Logger log = LoggerFactory.getLogger(AuditLogRegistrationClient.class);
    private final @NonNull AuditLogRegistrationService service;

    /**
     * Creates a new {@code AuditLogRegistrationClient} using the specified API base URL.
     *
     * @param baseUrl the base URL of the API; must not be {@code null}
     * @throws NullPointerException if {@code baseUrl} is {@code null} (from Retrofit)
     */
    public AuditLogRegistrationClient(@NonNull String baseUrl){
        this(new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build()
                .create(AuditLogRegistrationService.class)
        );
    }

    AuditLogRegistrationClient (@NonNull AuditLogRegistrationService service){
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    /**
     * Registers a guild for audit logging.
     *
     * @param guildId   the Discord guild ID (must not be {@code null})
     * @param channelId the Discord channel ID where audit logs should be sent (must not be {@code null})
     * @return {@code true} if the registration succeeded, {@code false} otherwise
     */
    public boolean registerGuild(@NonNull String guildId, @NonNull String channelId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");
        Objects.requireNonNull(channelId, "channelId cannot be null");

        try {
            return service
                    .registerGuild(new AuditLogRegistrationEntity(guildId, channelId))
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to register guild for Audit Logging [Guild ID={}]", guildId, e);
        }

        return false;
    }

    /**
     * Retrieves the audit log registration for a guild, if one exists.
     *
     * @param guildId the Discord guild ID (must not be {@code null})
     * @return an {@link Optional} containing the registration if found, or empty if not registered
     */
    public Optional<AuditLogRegistrationEntity> getRegisteredGuild (@NonNull String guildId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");

        try {
            return Optional.ofNullable(service
                    .getRegisteredGuild(guildId)
                    .execute().body());
        } catch (IOException e) {
            log.warn("Failed to retrieve guild registered for Audit Logging [Guild ID={}]", guildId, e);
        }

        return Optional.empty();
    }

    /**
     * Deletes the audit log registration for a guild.
     *
     * @param guildId the Discord guild ID (must not be {@code null})
     * @return {@code true} if the deletion succeeded, {@code false} otherwise
     */
    public boolean deleteRegisteredGuild (@NonNull String guildId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");

        try {
            return service
                    .deleteRegisteredGuild(guildId)
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to delete guild registered for Audit Logging [Guild ID={}]", guildId, e);
        }

        return false;

    }
}
