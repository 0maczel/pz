package pz.monitor.data.model;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="Resource")
public class Resource extends Entity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
