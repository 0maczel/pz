package pz.monitor.db.entity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="Metric")
public class Metric extends Entity {
	@Column(unique=true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
