package pz.monitor.service.measurement.complex;

/**
 * Complex measurement create request.
 * 
 * @author Invader
 *
 */
public class ComplexMeasuremetCreateRequest {

	private Long sensorId;
	private String name;
	private Long intervalMiliseconds;
	private Long windowMiliseconds;

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIntervalMiliseconds() {
		return intervalMiliseconds;
	}

	public void setIntervalMiliseconds(Long intervalMiliseconds) {
		this.intervalMiliseconds = intervalMiliseconds;
	}

	public Long getWindowMiliseconds() {
		return windowMiliseconds;
	}

	public void setWindowMiliseconds(Long windowMiliseconds) {
		this.windowMiliseconds = windowMiliseconds;
	}

}
