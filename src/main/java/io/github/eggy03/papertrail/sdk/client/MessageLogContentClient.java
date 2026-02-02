package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.ErrorEntity;
import io.github.eggy03.papertrail.sdk.entity.MessageLogContentEntity;
import io.github.eggy03.papertrail.sdk.http.HttpServiceEngine;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MessageLogContentClient {

    private final String baseUrl;

    public boolean logMessage(@NonNull String messageId, String messageContent, @NonNull String authorId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Either<ErrorEntity, MessageLogContentEntity> responseBody = HttpServiceEngine.makeRequestWithBody(
                HttpMethod.POST,
                baseUrl + "api/v1/content/message",
                headers,
                new MessageLogContentEntity(messageId, messageContent, authorId),
                MessageLogContentEntity.class
        );

        // log in case of failure
        responseBody.peekLeft(failure -> log.debug("Failed to log message with ID {}.\nAPI Response: {}", messageId, failure));

        return responseBody.isRight();
    }

    @NotNull
    public Optional<MessageLogContentEntity> retrieveMessage (@NonNull String messageId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Either<ErrorEntity, MessageLogContentEntity> response = HttpServiceEngine.makeRequest(
                HttpMethod.GET,
                baseUrl+"api/v1/content/message/"+messageId,
                headers,
                MessageLogContentEntity.class
        );

        // in case of error entity, log it
        response.peekLeft(error -> log.debug("Message of ID {} could not be retrieved.\nAPI Response: {}", messageId, error));

        // in case of success, return the never null MessageLogContentEntity object or empty optional
        return response.map(Optional::of).getOrElse(Optional.empty());
    }

    public boolean updateMessage (@NonNull String messageId, String messageContent, @NonNull String authorId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Either<ErrorEntity, MessageLogContentEntity> responseBody = HttpServiceEngine.makeRequestWithBody(
                HttpMethod.PUT,
                baseUrl+"api/v1/content/message",
                headers,
                new MessageLogContentEntity(messageId, messageContent, authorId),
                MessageLogContentEntity.class
        );

        responseBody.peekLeft(failure -> log.debug("Failed to update message with ID {}.\nAPI Response: {}", messageId, failure));

        return responseBody.isRight();
    }

    public boolean deleteMessage (@NonNull String messageId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Either<ErrorEntity, Void> responseBody = HttpServiceEngine.makeRequest(
                HttpMethod.DELETE,
                baseUrl+"api/v1/content/message/"+messageId,
                headers,
                Void.class
        );

        responseBody.peekLeft(failure -> log.debug("Failed to delete message with ID {}.\nAPI Response: {}", messageId, failure));

        return responseBody.isRight();
    }
}
