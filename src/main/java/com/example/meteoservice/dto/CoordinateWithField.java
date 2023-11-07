package com.example.meteoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoordinateWithField {
    private Double longitude;
    private Double latitude;
    private Long id;
}
