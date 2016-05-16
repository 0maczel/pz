package pz.monitor.db.entity;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@javax.persistence.Entity
@Table(name = "Sensor")
public class Sensor extends Entity {
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Resource resource;
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Metric metric;

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
