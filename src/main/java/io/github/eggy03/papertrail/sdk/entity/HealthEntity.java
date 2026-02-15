package io.github.eggy03.papertrail.sdk.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

@Getter
public class HealthEntity {

    @Nullable String status;
    @Nullable Components components;

    @JsonCreator
    public HealthEntity(@JsonProperty("status") @Nullable String status,
                        @JsonProperty("components") @Nullable Components components) {
        this.status = status;
        this.components = components;
    }

    @Getter
    public static class Components {

        @Nullable Database db;
        @Nullable Ping ping;
        @Nullable Redis redis;
        @Nullable Ssl ssl;

        @JsonCreator
        public Components(@JsonProperty("db") @Nullable Database db,
                          @JsonProperty("ping") @Nullable Ping ping,
                          @JsonProperty("redis") @Nullable Redis redis,
                          @JsonProperty("ssl") @Nullable Ssl ssl) {
            this.db = db;
            this.ping = ping;
            this.redis = redis;
            this.ssl = ssl;
        }

        @Data
        public static class Database {
            @Nullable String status;

            @JsonCreator
            public Database(@JsonProperty("status") @Nullable String status) {
                this.status = status;
            }
        }

        @Data
        public static class Ping {
            @Nullable String status;

            @JsonCreator
            public Ping(@JsonProperty("status") @Nullable String status) {
                this.status = status;
            }
        }

        @Data
        public static class Redis {
            @Nullable String status;

            @JsonCreator
            public Redis(@JsonProperty("status") @Nullable String status) {
                this.status = status;
            }
        }

        @Data
        public static class Ssl {
            String status;

            @JsonCreator
            public Ssl(@JsonProperty("status") @Nullable String status) {
                this.status = status;
            }
        }
    }
}
