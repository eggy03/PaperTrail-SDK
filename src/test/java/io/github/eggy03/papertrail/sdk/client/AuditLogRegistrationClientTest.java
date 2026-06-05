package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.AuditLogRegistrationEntity;
import io.github.eggy03.papertrail.sdk.service.AuditLogRegistrationService;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditLogRegistrationClientTest {

    private final String guildId = "123456789";
    private final String channelId = "987654321";

    private final AuditLogRegistrationEntity dummyEntity = new AuditLogRegistrationEntity(guildId, channelId);

    @Mock
    AuditLogRegistrationService service;

    AuditLogRegistrationClient client;

    @BeforeEach
    void setClient() {
        client = new AuditLogRegistrationClient(service);
    }

    @ParameterizedTest
    @EmptySource
    void testConstructorEmptyBaseUrl(String baseUrl) {
        assertThrows(IllegalArgumentException.class, () -> new AuditLogRegistrationClient(baseUrl));
    }

    @ParameterizedTest
    @NullSource
    void testConstructorNullBaseUrl(String baseUrl) {
        assertThrows(NullPointerException.class, () -> new AuditLogRegistrationClient(baseUrl));
    }

    @Test
    void registerGuild_success_returnsTrue() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);

        Response<AuditLogRegistrationEntity> successResponse = Response.success(new AuditLogRegistrationEntity(guildId, channelId));

        when(service.registerGuild(any(AuditLogRegistrationEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(successResponse);

        assertThat(client.registerGuild(guildId, channelId)).isTrue();

        verify(service).registerGuild(any(AuditLogRegistrationEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void registerGuild_error_returnsFalse() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);

        Response<AuditLogRegistrationEntity> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.registerGuild(any(AuditLogRegistrationEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        assertThat(client.registerGuild(guildId, channelId)).isFalse();

        verify(service).registerGuild(any(AuditLogRegistrationEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void registerGuild_throwsIOException_returnsFalse() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);

        when(service.registerGuild(any(AuditLogRegistrationEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        assertThat(client.registerGuild(guildId, channelId)).isFalse();

        verify(service).registerGuild(any(AuditLogRegistrationEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void getRegisteredGuild_success_returnsOptional() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);
        @SuppressWarnings("unchecked")
        Response<AuditLogRegistrationEntity> mockedResponse = mock(Response.class);

        when(service.getRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(mockedResponse);
        when(mockedResponse.body()).thenReturn(dummyEntity);

        Optional<AuditLogRegistrationEntity> result = client.getRegisteredGuild(anyString());
        assertThat(result).contains(dummyEntity);

        verify(service).getRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void getRegisteredGuild_error_returnsEmptyOptional() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);

        Response<AuditLogRegistrationEntity> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.getRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        Optional<AuditLogRegistrationEntity> result = client.getRegisteredGuild(anyString());
        assertThat(result).isEmpty();

        verify(service).getRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void getRegisteredGuild_throwsIOException() throws IOException {

        @SuppressWarnings("unchecked")
        Call<AuditLogRegistrationEntity> mockedCall = mock(Call.class);

        when(service.getRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        Optional<AuditLogRegistrationEntity> result = client.getRegisteredGuild(anyString());
        assertThat(result).isEmpty();

        verify(service).getRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteRegisteredGuild_success_returnsTrue() throws IOException {
        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);
        @SuppressWarnings("unchecked")
        Response<Void> mockedResponse = mock(Response.class);

        when(service.deleteRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(mockedResponse);
        when(mockedResponse.isSuccessful()).thenReturn(true);

        assertThat(client.deleteRegisteredGuild(anyString())).isTrue();

        verify(service).deleteRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteRegisteredGuild_error_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);

        Response<Void> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.deleteRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        assertThat(client.deleteRegisteredGuild(anyString())).isFalse();

        verify(service).deleteRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteRegisteredGuild_throwsIOException_returnsFalse() throws IOException {

        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);

        when(service.deleteRegisteredGuild(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        assertThat(client.deleteRegisteredGuild(anyString())).isFalse();

        verify(service).deleteRegisteredGuild(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }
}
