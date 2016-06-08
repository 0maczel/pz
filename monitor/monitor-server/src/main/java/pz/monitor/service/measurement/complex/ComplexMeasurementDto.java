package pz.monitor.service.measurement.complex;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import pz.monitor.service.common.Dto;

public class ComplexMeasurementDto implements Dto {

	private Long id;
	private String name;
	private String metric;
	private String resource;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
	private Timestamp creationTimestamp;
	private Long windowDurationMilis;
	private Long windowIntervalMilis;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Long getWindowDurationMilis() {
		return windowDurationMilis;
	}

	public void setWindowDurationMilis(Long windowDurationMilis) {
		this.windowDurationMilis = windowDurationMilis;
	}

	public Long getWindowIntervalMilis() {
		return windowIntervalMilis;
	}

	public void setWindowIntervalMilis(Long windowIntervalMilis) {
		this.windowIntervalMilis = windowIntervalMilis;
	}

}
