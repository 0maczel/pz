package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pz.monitor.service.metric.MetricDto;

import java.net.URI;
import java.util.List;

@Service
public class MetricService {

    @Value("${apiEndpoint}")
    String endpoint;

    @SuppressWarnings("unchecked")
    public List<MetricDto> getMetricByName(String name) {
        RestTemplate template = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/metrics");

        if (!StringUtils.isEmpty(name))
            builder.queryParam("name-like", name);

        URI targetUri = builder.build().toUri();

        return template.getForObject(targetUri, List.class);
    }

    public MetricDto getMetricById(Long id) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/metrics").
                pathSegment(String.valueOf(id)).
                build().toUri();

        return template.getForObject(targetUri, MetricDto.class);
    }

    public MetricDto createMetric(MetricDto dto) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/metrics").
                build().toUri();

        return template.postForObject(targetUri, dto, MetricDto.class);
    }
}
