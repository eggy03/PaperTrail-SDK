package io.github.eggy03.papertrail.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eggy03.papertrail.sdk.entity.MessageLogRegistrationEntity;
import io.github.eggy03.papertrail.sdk.service.MessageLogRegistrationService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Client for managing message log registrations via the PaperTrail API.
 */
@SuppressWarnings("java:S1192")
public final class MessageLogRegistrationClient {

    private static final Logger log = LoggerFactory.getLogger(MessageLogRegistrationClient.class);
    private final @NonNull MessageLogRegistrationService service;

    /**
     * Creates a new {@code MessageLogRegistrationClient} using the specified API base URL.
     *
     * @param baseUrl the base URL of the API; must not be {@code null}
     * @throws NullPointerException if {@code baseUrl} is {@code null} (from Retrofit)
     */
    public MessageLogRegistrationClient(@NonNull String baseUrl) {
        this(baseUrl, new ObjectMapper());
    }

    /**
     * Creates a new {@code MessageLogRegistrationClient} using the specified API base URL.
     *
     * @param baseUrl      the base URL of the API; must not be {@code null}
     * @param objectMapper the Jackson Object Mapper to use
     * @throws NullPointerException if {@code baseUrl} is {@code null} (from Retrofit)
     */
    public MessageLogRegistrationClient(@NonNull String baseUrl, @NonNull ObjectMapper objectMapper) {
        this(new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(MessageLogRegistrationService.class)
        );
    }

    MessageLogRegistrationClient(@NonNull MessageLogRegistrationService service) {
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    /**
     * Registers a guild for message logging.
     *
     * @param guildId   the Discord guild ID (must not be {@code null})
     * @param channelId the Discord channel ID where message logs should be sent (must not be {@code null})
     * @return {@code true} if the registration succeeded, {@code false} otherwise
     */
    public boolean registerGuild(@NonNull String guildId, @NonNull String channelId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");
        Objects.requireNonNull(channelId, "channelId cannot be null");

        try {
            return service
                    .registerGuild(new MessageLogRegistrationEntity(guildId, channelId))
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to register guild for Message Logging [Guild ID={}]", guildId, e);
        }

        return false;
    }

    /**
     * Retrieves the message log registration for a guild, if one exists.
     *
     * @param guildId the Discord guild ID (must not be {@code null})
     * @return an {@link Optional} containing the registration if found, or empty if not registered
     */
    public Optional<MessageLogRegistrationEntity> getRegisteredGuild(@NonNull String guildId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");

        try {
            return Optional.ofNullable(service
                    .getRegisteredGuild(guildId)
                    .execute().body());
        } catch (IOException e) {
            log.warn("Failed to retrieve guild registered for Message Logging [Guild ID={}]", guildId, e);
        }

        return Optional.empty();
    }

    /**
     * Deletes the message log registration for a guild.
     *
     * @param guildId the Discord guild ID (must not be {@code null})
     * @return {@code true} if the deletion succeeded, {@code false} otherwise
     */
    public boolean deleteRegisteredGuild(@NonNull String guildId) {

        Objects.requireNonNull(guildId, "guildId cannot be null");

        try {
            return service
                    .deleteRegisteredGuild(guildId)
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to delete guild registered for Message Logging [Guild ID={}]", guildId, e);
        }

        return false;

    }
}
