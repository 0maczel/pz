package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import pz.monitor.service.metric.MetricDto;
import pz.webclient.path.Paths;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MetricService {

    @Value("${apiEndpoint}")
    String endpoint;

    public List<MetricDto> getMetricByName(String name) {
        RestTemplate template = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/metrics");

        if (!StringUtils.isEmpty(name))
            builder.queryParam("name-like", name);

        URI targetUri = builder.build().toUri();
        MetricDto[] metrics = template.getForObject(targetUri, MetricDto[].class);
        return Arrays.asList(metrics);
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
    
    public MetricDto getMetricFromUri(String uri) {
    	if(StringUtils.isEmpty(uri))
    		return null;
    	UriTemplate uriTemplate = new UriTemplate(endpoint + Paths.METRIC);
    	Map<String, String> map = uriTemplate.match(uri);
    	String idString = map.get(Paths.METRIC_ID);
    	Long id = null;
    	try{
    		id = Long.valueOf(idString);	
    	} catch (NumberFormatException e) {
    		return null;
    	}
    	return getMetricById(id);
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
