package pz.monitor.service.measurement.complex;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Sensor;
import pz.monitor.service.common.DtoConverter;

@RestController
@Transactional
public class ComplexMeasurementService {

	@Autowired
	private Repository repository;

	@Autowired
	private DtoConverter<ComplexMeasurement, ComplexMeasurementDto> complexMeasurementDtoConverter;

	@RequestMapping(value = "/complex-measurement", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Measurement create(@RequestBody ComplexMeasuremetCreateRequest request) {
		Long sensorId = Objects.requireNonNull(request.getSensorId());
		Sensor sensor = repository.getChecked(Sensor.class, sensorId);

		ComplexMeasurement createCandidate = new ComplexMeasurement();
		createCandidate.setName(request.getName());
		createCandidate.setSensor(sensor);
		createCandidate.setWindowDurationMilis(request.getWindowMiliseconds());
		createCandidate.setWindowIntervalMilis(request.getIntervalMiliseconds());

		repository.save(createCandidate);
		return null;
	}

	@RequestMapping(value = "/complex-measurement/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		repository.delete(ComplexMeasurement.class, id);
	}

	@RequestMapping(value = "/complex-measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<ComplexMeasurementDto> listAll() {
		List<ComplexMeasurement> all = repository.all(ComplexMeasurement.class);
		return complexMeasurementDtoConverter.covertListToDto(all);
	}

}
