package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.ErrorEntity;
import io.github.eggy03.papertrail.sdk.entity.HealthEntity;
import io.github.eggy03.papertrail.sdk.exception.ApiBaseUrlException;
import io.github.eggy03.papertrail.sdk.http.HttpServiceEngine;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Optional;

@Slf4j
public class HealthClient {

    private final String baseUrl;

    public HealthClient(String baseUrl){
        if(baseUrl==null || baseUrl.trim().isEmpty())
            throw new ApiBaseUrlException("Base URL is null or empty");

        this.baseUrl = baseUrl;
    }

    @NotNull
    public Optional<HealthEntity> getHealth() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Either<ErrorEntity, HealthEntity> response = HttpServiceEngine.makeRequest(
                HttpMethod.GET,
                baseUrl + "actuator/health",
                headers,
                HealthEntity.class
        );

        response.peekLeft(errorEntity -> log.debug("Failed to get health metrics for API {}", errorEntity));

        return response.map(Optional::ofNullable).getOrElse(Optional.empty());
    }
}
