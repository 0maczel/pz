package pz.monitor.service.sensor;

import pz.monitor.service.common.Dto;

public class SensorDto implements Dto {
	private Long id;
	private String resource;
	private String metric;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}
}
