package pz.monitor.data.model;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="Metric")
public class Metric extends Entity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
