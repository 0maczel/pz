package pz.monitor.service.sensor;

/**
 * Create request for sensor.
 * 
 * @author Invader
 *
 */
public class SensorCreateRequest {
	private Long metricId;
	private Long resourceId;

	public Long getMetricId() {
		return metricId;
	}

	public void setMetricId(Long metricId) {
		this.metricId = metricId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
