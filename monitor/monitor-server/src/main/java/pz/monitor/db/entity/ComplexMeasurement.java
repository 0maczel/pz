package pz.monitor.db.entity;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@javax.persistence.Entity
@Table(name = "ComplexMeasurement")
public class ComplexMeasurement extends Entity {

	private String name;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Sensor sensor;

	private Long windowDurationMilis;
	private Long windowIntervalMilis;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Long getWindowDurationMilis() {
		return windowDurationMilis;
	}

	public void setWindowDurationMilis(Long windowDurationMilis) {
		this.windowDurationMilis = windowDurationMilis;
	}

	public Long getWindowIntervalMilis() {
		return windowIntervalMilis;
	}

	public void setWindowIntervalMilis(Long windowIntervalMilis) {
		this.windowIntervalMilis = windowIntervalMilis;
	}

}
