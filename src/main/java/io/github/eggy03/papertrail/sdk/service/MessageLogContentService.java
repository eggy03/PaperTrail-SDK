package io.github.eggy03.papertrail.sdk.service;

import io.github.eggy03.papertrail.sdk.entity.MessageLogContentEntity;
import org.jspecify.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MessageLogContentService {

    @POST("api/v1/content/message")
    Call<MessageLogContentEntity> logMessage(@Body MessageLogContentEntity requestBody);

    @GET("api/v1/content/message/{messageId}")
    Call<MessageLogContentEntity> retrieveMessage(@Path("messageId") @NonNull String messageId);

    @PUT("api/v1/content/message")
    Call<MessageLogContentEntity> updateMessage(@Body MessageLogContentEntity requestBody);

    @DELETE("api/v1/content/message/{messageId}")
    Call<Void> deleteMessage(@Path("messageId") @NonNull String messageId);
}
