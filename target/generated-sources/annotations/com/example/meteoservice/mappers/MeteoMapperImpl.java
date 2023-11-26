package com.example.meteoservice.mappers;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dto.MeteoResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-25T18:06:19+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MeteoMapperImpl implements MeteoMapper {

    @Override
    public MeteoResponse meteoToMeteoResponse(Meteo meteo) {
        if ( meteo == null ) {
            return null;
        }

        MeteoResponse meteoResponse = new MeteoResponse();

        meteoResponse.setDay( meteo.getDay() );
        meteoResponse.setFieldId( meteo.getFieldId() );
        meteoResponse.setTemperature( meteo.getTemperature() );
        meteoResponse.setHumidity( meteo.getHumidity() );
        meteoResponse.setPressure( meteo.getPressure() );

        return meteoResponse;
    }
}
