package pz.monitor.data.model;

import java.time.Duration;

public class ComplexMeasurement extends Entity {
	private Resource resource;
	private Metric metric;
	private Duration windowLength;
	private Duration windowInterval;

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

	public Duration getWindowLength() {
		return windowLength;
	}

	public void setWindowLength(Duration windowLength) {
		this.windowLength = windowLength;
	}

	public Duration getWindowInterval() {
		return windowInterval;
	}

	public void setWindowInterval(Duration windowInterval) {
		this.windowInterval = windowInterval;
	}
}
