package com.example.meteoservice.mappers;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dto.MeteoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeteoMapper {
    MeteoResponse meteoToMeteoResponse(Meteo meteo);
}
