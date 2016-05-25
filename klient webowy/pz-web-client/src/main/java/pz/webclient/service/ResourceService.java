package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pz.monitor.service.resource.ResourceDto;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class ResourceService {

    @Value("${apiEndpoint}")
    String endpoint;

    public List<ResourceDto> getResourceByName(String name) {
        RestTemplate template = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/resources");

        if (!StringUtils.isEmpty(name))
            builder.queryParam("name-like", name);

        URI targetUri = builder.build().toUri();
        ResourceDto[] resources = template.getForObject(targetUri, ResourceDto[].class);
        return Arrays.asList(resources);
    }

    public ResourceDto getResourceById(Long id) {
        RestTemplate template = new RestTemplate();

        URI targetUri = UriComponentsBuilder.
                fromUriString(endpoint).
                path("/resources").
                pathSegment(String.valueOf(id)).
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
