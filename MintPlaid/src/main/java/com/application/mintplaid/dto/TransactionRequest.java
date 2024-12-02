package com.application.mintplaid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @JsonProperty("start_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String startDate;

    @JsonProperty("end_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String endDate;

    private Integer page;
    private Integer size;

}
