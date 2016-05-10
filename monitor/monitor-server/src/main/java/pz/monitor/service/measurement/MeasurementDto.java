package pz.monitor.service.measurement;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import pz.monitor.service.common.Dto;

public class MeasurementDto implements Dto {
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
	private Timestamp creationTimestamp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
	private Timestamp updateTimestamp;
	private double value;
	private String resource;
	private String metric;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
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
