package pz.monitor.data.model;

public class Sensor extends Entity {
	private String externalSystemId;
	private Resource resource;
	private Metric metric;

	public String getExternalSystemId() {
		return externalSystemId;
	}

	public void setExternalSystemId(String externalSystemId) {
		this.externalSystemId = externalSystemId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}
}
