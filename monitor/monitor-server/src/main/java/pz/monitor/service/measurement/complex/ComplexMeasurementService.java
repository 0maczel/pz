package pz.monitor.service.measurement.complex;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryFinalizer;
import pz.monitor.db.query.QueryInitializer;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;
import pz.monitor.service.measurement.MeasurementDto;


@RestController
@Transactional
public class ComplexMeasurementService {

	@Autowired
	private Repository repository;

	@Autowired
	private ComplexMeasurementDao dao;

	@Autowired
	private DtoConverter<ComplexMeasurement, ComplexMeasurementDto> complexMeasurementDtoConverter;

	@Autowired
	private UriHelper uriHelper;

	@Autowired
	private ResourceMapping<Resource> resourceMapping;

	@Autowired
	private ResourceMapping<Metric> metricMapping;

	@RequestMapping(value = "/complex-measurement", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Measurement create(@RequestBody ComplexMeasuremetCreateRequest request) {
		Long sensorId = Objects.requireNonNull(request.getSensorId());
		Sensor sensor = repository.getChecked(Sensor.class, sensorId);

		ComplexMeasurement createCandidate = new ComplexMeasurement();
		createCandidate.setName(request.getName());
		createCandidate.setSensor(sensor);
		createCandidate.setWindowDurationMilis(request.getWindowMiliseconds());
		createCandidate.setWindowIntervalMilis(request.getIntervalMiliseconds());

		dao.save(createCandidate);
		return null;
	}

	@RequestMapping(value = "/complex-measurement/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		dao.delete(id);
	}

	@RequestMapping(value = "/complex-measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<ComplexMeasurementDto> listAll() {
		List<ComplexMeasurement> all = dao.list();
		return complexMeasurementDtoConverter.covertListToDto(all);
	}

	@RequestMapping(path = "/sensors/{sensorId}/complex-measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<MeasurementDto> listAllForSensor(@PathVariable Long sensorId) {

		List<ComplexMeasurement> listForSensor = dao.listForSensor(sensorId);
		return Arrays.asList();
	}

	@RequestMapping(path = "/resource/{resourceId}/complex-measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<MeasurementDto> listAllForResource(@PathVariable Long resourceId) {

		List<ComplexMeasurement> listForResource = dao.listForResource(resourceId);
		return Arrays.asList();
	}

	/**
	 * Ta metoda miałaby wygenrować listę pomiarów prostych w oparciu o pomiary
	 * złożone (może być w teorii kilka pomiarów złożonych dla sensora).
	 * 
	 * @param complexMeasurements
	 * @return
	 */
	public List<MeasurementDto> generateComplexMeasurements(List<ComplexMeasurement> complexMeasurements) {
		List<MeasurementDto> arrayList = new ArrayList<>();
		for (ComplexMeasurement cmp : complexMeasurements) {
			arrayList.add(generateSingleMeasurement(cmp));
		}
		return arrayList;
	}

	/**
	 * Ta metoda wyciagaproste pomiary na podstawie rozmiaru okna
	 * pomiaru złożonego, agreguje je i wylicza srednia�. Calosc zostaje zwrocona
	 * zwrócona jako pojedyczny {@link MeasurementDto}, z wartoscia wyliczona. Pozostale 
	 * dane pochodza z complexMeasurement.
	 * 
	 *
	 */
	private MeasurementDto generateSingleMeasurement(ComplexMeasurement complexMeasurement) {
		final Sensor sensor = complexMeasurement.getSensor();
		final String resourceUri = uriHelper.resourcePath(resourceMapping, sensor.getResource());
		final String metricUri = uriHelper.resourcePath(metricMapping, sensor.getMetric());
		long windowDuration = complexMeasurement.getWindowDurationMilis();
		long windowInterval = complexMeasurement.getWindowIntervalMilis();
		
		Measurement finalMeasurement = new Measurement();
		finalMeasurement.setMetric(sensor.getMetric());
		finalMeasurement.setResource(sensor.getResource());
		finalMeasurement.setSensor(sensor);

		QueryInitializer queryBuilder = new QueryBuilder();
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		
		QueryFinalizer<Measurement> preparedQuery = queryBuilder.queryFor(Measurement.class).that("creationTimestamp", Is.lessThan(timestamp))
				.and().that("creationTimestamp", Is.greaterThan(new Timestamp(timestamp.getSeconds()*100 + windowDuration)))	   
				.and().with("metric").that("name", Is.equalTo(sensor.getMetric().getName() ))
				.and().with("resource").that("name", Is.like(sensor.getResource().getName() ))
				.and().orderBy("creationTimestamp").desc()
				.and().orderBy("value").asc();
	
		Query<Measurement> query = preparedQuery.build();
		List<Measurement> measurements = repository.query(query);
		
		MeasurementDto singleMeasurement = new MeasurementDto();
		singleMeasurement.setMetric(metricUri);
		singleMeasurement.setResource(resourceUri);
		singleMeasurement.setValue(this.calculateAverageValueOfMeasurement(measurements));
		
		return singleMeasurement;
	}

	private Measurement[] agregateMeasurementsToJson(QueryFinalizer<Measurement> preparedQuery, Timestamp actualTimestamp, long windowInterval, long windowDuration){
		
		Timestamp from = actualTimestamp;
		ArrayList<ArrayList<Measurement>> measurements = new ArrayList<ArrayList<Measurement>>();
		for (int i =0; i< Math.ceil(windowDuration/windowInterval); ){
			int index = 0;
			ArrayList<Measurement> agregatedMeasurements = new ArrayList<Measurement>();
			Query<Measurement> query = preparedQuery.and().that("creationTimestamp", Is.lessThan( new Timestamp(actualTimestamp.getSeconds()*100 + windowDuration))).build();
			List<Measurement> queryResult = repository.query(query);
			for (Measurement measurement : queryResult){
				agregatedMeasurements.add(measurement);
			}
			measurements.set(index, agregatedMeasurements);
			actualTimestamp = new Timestamp(actualTimestamp.getSeconds()*100 + windowInterval);
			index++;
		}
		return (Measurement[]) measurements.toArray();
	} 
	
	private double calculateAverageValueOfMeasurement(List<Measurement> measurements){
		int counter = 0;
		double sum = 0.0;
		for (Measurement measurement : measurements){
			counter++;
			sum += measurement.getValue();
		}
		return sum/(double)counter;
	}
	
	
}
