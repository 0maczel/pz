package pz.webclient.service;

import org.springframework.stereotype.Service;
import pz.monitor.service.measurement.MeasurementDto;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MeasurementsService {

    public MeasurementDto getMeasurementById(long id) {
        return null;
    }

    public List<MeasurementDto> getMeasurements(String resourceLike, String metricLike, ZonedDateTime fromDate, ZonedDateTime toDate, Long limit) {
        return null;
    }

}
