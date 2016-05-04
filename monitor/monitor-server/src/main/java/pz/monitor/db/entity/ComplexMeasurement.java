package pz.monitor.db.entity;

import java.time.Duration;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@javax.persistence.Entity
@Table(name="ComplexMeasurement")
public class ComplexMeasurement extends Entity {
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Resource resource;
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
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
