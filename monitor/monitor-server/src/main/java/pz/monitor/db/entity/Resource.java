package pz.monitor.db.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "Resource")
public class Resource extends Entity {
	@Column(unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
