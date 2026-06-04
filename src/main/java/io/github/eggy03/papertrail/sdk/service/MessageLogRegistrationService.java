package io.github.eggy03.papertrail.sdk.service;

import io.github.eggy03.papertrail.sdk.entity.MessageLogRegistrationEntity;
import org.jspecify.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageLogRegistrationService {

    @POST("api/v1/log/message")
    Call<MessageLogRegistrationEntity> registerGuild(@Body MessageLogRegistrationEntity requestBody);

    @GET("api/v1/log/message/{guildId}")
    Call<MessageLogRegistrationEntity> getRegisteredGuild(@Path ("guildId") @NonNull String guildId);

    @DELETE("api/v1/log/message/{guildId}")
    Call<MessageLogRegistrationEntity> deleteRegisteredGuild(@Path ("guildId") @NonNull String guildId);
}
