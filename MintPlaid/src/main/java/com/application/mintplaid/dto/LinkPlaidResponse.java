package com.application.mintplaid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkPlaidResponse {
    @JsonProperty("link_token")
    private String linkToken;

    @JsonProperty("expiration")
    private String expiration;

    @JsonProperty("request_id")
    private String requestId;
}
