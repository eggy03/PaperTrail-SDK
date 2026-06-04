package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.MessageLogContentEntity;
import io.github.eggy03.papertrail.sdk.service.MessageLogContentService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Client for managing stored message content via the PaperTrail API.
 */
@SuppressWarnings("java:S1192")
public final class MessageLogContentClient {

    private static final Logger log = LoggerFactory.getLogger(MessageLogContentClient.class);
    private final @NonNull MessageLogContentService service;

    /**
     * Creates a new {@code MessageLogContentClient} using the specified API base URL.
     *
     * @param baseUrl the base URL of the API; must not be {@code null}
     * @throws NullPointerException if {@code baseUrl} is {@code null} (from Retrofit)
     */
    public MessageLogContentClient(@NonNull String baseUrl){
        this(new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build()
                .create(MessageLogContentService.class)
        );
    }

    MessageLogContentClient (@NonNull MessageLogContentService service){
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    /**
     * Logs a new message's content.
     *
     * @param messageId      the Discord message ID (must not be {@code null})
     * @param messageContent the content of the message (must not be {@code null} but may be empty)
     * @param authorId       the Discord user ID of the message author (must not be {@code null})
     * @return {@code true} if the message was logged successfully, {@code false} otherwise
     */
    public boolean logMessage(@NonNull String messageId, @NonNull String messageContent, @NonNull String authorId) {

        try {
            return service
                    .logMessage(new MessageLogContentEntity(messageId, messageContent, authorId))
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to log message [id={}]", messageId, e);
        }

        return false;
    }

    /**
     * Retrieves a logged message by its ID.
     *
     * @param messageId the Discord message ID (must not be {@code null})
     * @return an {@link Optional} containing the message content if found, or empty if not present
     */
    public @NonNull Optional<MessageLogContentEntity> retrieveMessage (@NonNull String messageId) {

        Objects.requireNonNull(messageId, "messageId cannot be null");

        try {
            return Optional.ofNullable(service
                    .retrieveMessage(messageId)
                    .execute()
                    .body());
        } catch (IOException e) {
            log.warn("Failed to retrieve message [id={}]", messageId, e);
        }

        return Optional.empty();
    }

    /**
     * Updates the content of an already logged message.
     *
     * @param messageId      the Discord message ID (must not be {@code null})
     * @param messageContent the updated message content (must not be {@code null})
     * @param authorId       the Discord user ID of the message author (must not be {@code null})
     * @return {@code true} if the update succeeded, {@code false} otherwise
     */
    public boolean updateMessage (@NonNull String messageId, @NonNull String messageContent, @NonNull String authorId) {

        Objects.requireNonNull(messageId, "messageId cannot be null");
        Objects.requireNonNull(messageContent, "messageContent cannot be null");
        Objects.requireNonNull(authorId, "authorId cannot be null");

        try {
            return service
                    .updateMessage(new MessageLogContentEntity(messageId, messageContent, authorId))
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to update message [id={}]", messageId, e);
        }

        return false;
    }

    /**
     * Deletes a logged message by its ID.
     *
     * @param messageId the Discord message ID (must not be {@code null})
     * @return {@code true} if the deletion succeeded, {@code false} otherwise
     */
    public boolean deleteMessage (@NonNull String messageId) {

        Objects.requireNonNull(messageId, "messageId cannot be null");

        try {
            return service
                    .deleteMessage(messageId)
                    .execute()
                    .isSuccessful();
        } catch (IOException e) {
            log.warn("Failed to delete message [id={}]", messageId, e);
        }

        return false;
    }
}
