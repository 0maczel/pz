package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pz.monitor.service.measurement.MeasurementDto;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class MeasurementsService {

    @Value("${apiEndpoint}")
    String endpoint;

    public MeasurementDto getMeasurementById(long id) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/measurements").
                pathSegment(String.valueOf(id)).
                build().toUri();

        return template.getForObject(targetUri, MeasurementDto.class);
    }

    public List<MeasurementDto> getMeasurements(String resourceLike, String metricLike, ZonedDateTime fromDate, ZonedDateTime toDate, Long limit) {
        RestTemplate template = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/measurements");

        if (!StringUtils.isEmpty(resourceLike))
            builder.queryParam("resource-like", resourceLike);

        if (!StringUtils.isEmpty(metricLike))
            builder.queryParam("metric-like", metricLike);

        if (fromDate != null)
            builder.queryParam("from-date", fromDate);

        if (toDate != null)
            builder.queryParam("to-date", toDate);

        if (limit > 0)
            builder.queryParam("limit", limit);

        URI targetUri = builder.build().toUri();
        MeasurementDto[] measurements = template.getForObject(targetUri, MeasurementDto[].class);
        return Arrays.asList(measurements);
    }

}
