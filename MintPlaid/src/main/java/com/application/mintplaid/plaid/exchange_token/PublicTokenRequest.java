package com.application.mintplaid.plaid.exchange_token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicTokenRequest {
    @JsonProperty(value = "publicToken", required = true)
    private String publicToken;

}
