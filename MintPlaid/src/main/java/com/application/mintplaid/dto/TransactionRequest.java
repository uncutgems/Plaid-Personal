package com.application.mintplaid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @JsonProperty("start_date")
    @NotEmpty
    private String startDate;

    @JsonProperty("end_date")
    @NotEmpty
    private String endDate;

    private Integer page;
    private Integer size;

}
