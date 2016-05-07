package pz.monitor.service.measurement;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;

@RestController
@Transactional
public class MeasurementService {
	@Autowired
	Repository repository;
	
	@RequestMapping(path = "/measurements/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Measurement get(@PathVariable Long id)
    {
        return repository.get(Measurement.class, id);
    }
	
	@RequestMapping(path = "/measurements/random", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Measurement newRandom()
    {
		Measurement m = new Measurement();
		Random r = new Random();
		m.setValue(r.nextDouble());
        repository.save(m);
        return m;
    }
}
