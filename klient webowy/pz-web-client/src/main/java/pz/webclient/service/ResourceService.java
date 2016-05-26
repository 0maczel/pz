package pz.webclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import pz.monitor.service.resource.ResourceDto;
import pz.webclient.path.Paths;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ResourceService {

	@Value("${apiEndpoint}")
	String endpoint;

	public List<ResourceDto> getResourceByName(String name) {
		RestTemplate template = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpoint).path("/resources");

		if (!StringUtils.isEmpty(name))
			builder.queryParam("name-like", name);

		URI targetUri = builder.build().toUri();
		ResourceDto[] resources = template.getForObject(targetUri, ResourceDto[].class);
		return Arrays.asList(resources);
	}

	public ResourceDto getResourceById(Long id) {
		RestTemplate template = new RestTemplate();

		URI targetUri = UriComponentsBuilder.fromUriString(endpoint).path("/resources").pathSegment(String.valueOf(id))
				.build().toUri();

		return template.getForObject(targetUri, ResourceDto.class);
	}

	public ResourceDto createResource(ResourceDto dto) {
		RestTemplate template = new RestTemplate();

		URI targetUri = UriComponentsBuilder.fromUriString(endpoint).path("/resources").build().toUri();

		return template.postForObject(targetUri, dto, ResourceDto.class);
	}

	public ResourceDto getResourceFromUri(String uri) {
		if (StringUtils.isEmpty(uri))
			return null;
		UriTemplate uriTemplate = new UriTemplate(endpoint + Paths.RESOURCE);
		Map<String, String> map = uriTemplate.match(uri);
		String idString = map.get(Paths.RESOURCE_ID);
		Long id = null;
		try {
			id = Long.valueOf(idString);
		} catch (NumberFormatException e) {
			return null;
		}
		return getResourceById(id);
	}
}
