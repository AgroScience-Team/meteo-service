package com.example.meteoservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeteoResponse {
    private LocalDate day;
    private Long fieldId;
    private Double temperature;
    private Double humidity;
    private Double pressure;
}
