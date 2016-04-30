package pz.monitor.data.model;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@javax.persistence.Entity
@Table(name="Entity")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Entity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Version
	private int entityVersion;
	@CreationTimestamp
	private Timestamp creationTimestamp;
	@UpdateTimestamp
	private Timestamp updateTimestamp;

	public int getId() {
		return id;
	}

	public int getEntityVersion() {
		return entityVersion;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}
}
