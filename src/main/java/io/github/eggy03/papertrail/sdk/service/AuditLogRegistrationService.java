package io.github.eggy03.papertrail.sdk.service;

import io.github.eggy03.papertrail.sdk.entity.AuditLogRegistrationEntity;
import org.jspecify.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuditLogRegistrationService {

    @POST("api/v1/log/audit")
    Call<AuditLogRegistrationEntity> registerGuild(@Body AuditLogRegistrationEntity requestBody);

    @GET("api/v1/log/audit/{guildId}")
    Call<AuditLogRegistrationEntity> getRegisteredGuild(@Path("guildId") @NonNull String guildId);

    @DELETE("api/v1/log/audit/{guildId}")
    Call<Void> deleteRegisteredGuild(@Path("guildId") @NonNull String guildId);
}
