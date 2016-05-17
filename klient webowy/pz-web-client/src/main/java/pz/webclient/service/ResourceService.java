package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pz.monitor.service.resource.ResourceDto;

import java.net.URI;
import java.util.List;

@Service
public class ResourceService {

    @Value("${apiEndpoint}")
    String endpoint;

    @SuppressWarnings("unchecked")
    public List<ResourceDto> getResourceByName(String name) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/resources").
                queryParam("name-like", name).
                build().toUri();

        return template.getForObject(targetUri, List.class);
    }

    public ResourceDto getResourceById(Long id) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/resources").
                pathSegment("/" + String.valueOf(id)).
                build().toUri();

        return template.getForObject(targetUri, ResourceDto.class);
    }

    public ResourceDto createResource(ResourceDto dto) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/resources").
                build().toUri();

        return template.postForObject(targetUri, dto, ResourceDto.class);
    }
}
