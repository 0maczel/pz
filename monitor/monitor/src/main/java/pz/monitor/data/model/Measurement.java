package pz.monitor.data.model;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@javax.persistence.Entity
@Table(name="Measurement")
public class Measurement extends Entity {
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Resource resource;
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Metric metric;
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Sensor sensor;
	private double value;

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

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
