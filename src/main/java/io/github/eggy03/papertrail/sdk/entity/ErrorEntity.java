package io.github.eggy03.papertrail.sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder(toBuilder = true)
public class ErrorEntity {

    final int status;
    final String error;
    final String message;
    final String timeStamp;
    final String path;

}
