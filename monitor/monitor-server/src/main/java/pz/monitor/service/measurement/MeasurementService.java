package pz.monitor.service.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.service.common.UriHelper;

@RestController
@Transactional
public class MeasurementService {
	@Autowired
	Repository repository;

	@RequestMapping(path = "/measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<String> get(@RequestParam(name = "id", required = false, defaultValue="0") int id) {
		List<Measurement> resources = new ArrayList<>();
		
		if(id != 0) {	
			resources.add(repository.get(Measurement.class, new Long(id)));
		}
		else {
			resources = repository.all(Measurement.class);
		}
		return UriHelper.resourcePath(resources);
	}

	@RequestMapping(path = "/measurements/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Measurement get(@PathVariable Long id) {
		return repository.get(Measurement.class, id);
	}

	@RequestMapping(path = "/measurements/random", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String newRandom() {
		Measurement m = new Measurement();
		Random r = new Random();
		m.setValue(r.nextDouble());
		repository.save(m);
		return UriHelper.resourcePath("/measurements", m);
	}

}
