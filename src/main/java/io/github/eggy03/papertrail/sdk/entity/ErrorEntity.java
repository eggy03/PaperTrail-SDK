package io.github.eggy03.papertrail.sdk.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ErrorEntity {

    int status;
    String error;
    String message;
    String timeStamp;
    String path;

}
