package io.github.eggy03.papertrail.sdk.client;

import io.github.eggy03.papertrail.sdk.entity.MessageLogContentEntity;
import io.github.eggy03.papertrail.sdk.service.MessageLogContentService;
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
class MessageLogContentClientTest {

    private final String messageId = "123456789";
    private final String messageContent = "test";
    private final String authorId = "987654321";

    private final MessageLogContentEntity dummyEntity = new MessageLogContentEntity(messageId, messageContent, authorId);

    @Mock
    MessageLogContentService service;

    MessageLogContentClient client;

    @BeforeEach
    void setClient() {
        client = new MessageLogContentClient(service);
    }

    @ParameterizedTest
    @EmptySource
    void testConstructorEmptyBaseUrl(String baseUrl) {
        assertThrows(IllegalArgumentException.class, () -> new MessageLogContentClient(baseUrl));
    }

    @ParameterizedTest
    @NullSource
    void testConstructorNullBaseUrl(String baseUrl) {
        assertThrows(NullPointerException.class, () -> new MessageLogContentClient(baseUrl));
    }

    @Test
    void logMessage_success_returnsTrue() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        Response<MessageLogContentEntity> successResponse = Response.success(dummyEntity);

        when(service.logMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(successResponse);

        assertThat(client.logMessage(messageId, messageContent, authorId)).isTrue();

        verify(service).logMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void logMessage_error_returnsFalse() throws IOException {

        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        Response<MessageLogContentEntity> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.logMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        assertThat(client.logMessage(messageId, messageContent, authorId)).isFalse();

        verify(service).logMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);

    }

    @Test
    void logMessage_throwsIOException_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        when(service.logMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        assertThat(client.logMessage(messageId, messageContent, authorId)).isFalse();

        verify(service).logMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void retrieveMessage_success_returnsOptional() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);
        @SuppressWarnings("unchecked")
        Response<MessageLogContentEntity> mockedResponse = mock(Response.class);

        when(service.retrieveMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(mockedResponse);
        when(mockedResponse.body()).thenReturn(dummyEntity);

        Optional<MessageLogContentEntity> result = client.retrieveMessage(anyString());
        assertThat(result).contains(dummyEntity);

        verify(service).retrieveMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void retrieveMessage_error_returnsEmptyOptional() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);
        
        Response<MessageLogContentEntity> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.retrieveMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);
        
        Optional<MessageLogContentEntity> result = client.retrieveMessage(anyString());
        assertThat(result).isEmpty();

        verify(service).retrieveMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void retrieveMessage_throwsIOException_returnsEmptyOptional() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        when(service.retrieveMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        Optional<MessageLogContentEntity> result = client.retrieveMessage(anyString());
        assertThat(result).isEmpty();

        verify(service).retrieveMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void updateMessage_success_returnsTrue() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        Response<MessageLogContentEntity> successResponse = Response.success(dummyEntity);

        when(service.updateMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(successResponse);

        assertThat(client.updateMessage(messageId, messageContent, authorId)).isTrue();

        verify(service).updateMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void updateMessage_error_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        Response<MessageLogContentEntity> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.updateMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        assertThat(client.updateMessage(messageId, messageContent, authorId)).isFalse();

        verify(service).updateMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void updateMessage_throwsIOException_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<MessageLogContentEntity> mockedCall = mock(Call.class);

        when(service.updateMessage(any(MessageLogContentEntity.class))).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        assertThat(client.updateMessage(messageId, messageContent, authorId)).isFalse();

        verify(service).updateMessage(any(MessageLogContentEntity.class));
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteMessage_success_returnsTrue() throws IOException {
        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);
        @SuppressWarnings("unchecked")
        Response<Void> successResponse = mock(Response.class);

        when(service.deleteMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(successResponse);
        when(successResponse.isSuccessful()).thenReturn(true);

        assertThat(client.deleteMessage(messageId)).isTrue();

        verify(service).deleteMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteMessage_error_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);

        Response<Void> errorResponse = Response.error(400, ResponseBody.create("", null));

        when(service.deleteMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenReturn(errorResponse);

        assertThat(client.deleteMessage(messageId)).isFalse();

        verify(service).deleteMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }

    @Test
    void deleteMessage_throwsIOException_returnsFalse() throws IOException {
        @SuppressWarnings("unchecked")
        Call<Void> mockedCall = mock(Call.class);

        when(service.deleteMessage(anyString())).thenReturn(mockedCall);
        when(mockedCall.execute()).thenThrow(IOException.class);

        assertThat(client.deleteMessage(messageId)).isFalse();

        verify(service).deleteMessage(anyString());
        verifyNoMoreInteractions(mockedCall, service);
    }
}
