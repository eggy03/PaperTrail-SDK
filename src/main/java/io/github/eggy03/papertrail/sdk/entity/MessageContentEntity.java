package io.github.eggy03.papertrail.sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@Getter
@Builder(toBuilder = true)
public class MessageContentEntity {

    @Nullable
    final String messageId;

    @Nullable
    final String messageContent;

    @Nullable
    final String authorId;
}
