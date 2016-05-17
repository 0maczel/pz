package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pz.monitor.service.sensor.SensorDto;

import java.net.URI;
import java.util.List;

@Service
public class SensorService {

    @Value("${apiEndpoint}")
    String endpoint;


    @SuppressWarnings("unchecked")
    public List<SensorDto> getSensor(String resourceName, String metricName) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/sensors").
                queryParam("resource-name", resourceName).
                queryParam("metric-name", metricName).
                build().toUri();

        return template.getForObject(targetUri, List.class);

    }
    public SensorDto getSensorById(Long id) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/sensors").
                pathSegment("/" + String.valueOf(id)).
                build().toUri();

        return template.getForObject(targetUri, SensorDto.class);
    }
}
