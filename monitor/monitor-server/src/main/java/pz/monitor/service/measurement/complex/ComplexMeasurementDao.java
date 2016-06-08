package pz.monitor.service.measurement.complex;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.ComplexMeasurement;

/**
 * Dao pattern for Complex measurement.
 * 
 * @author Invader
 *
 */
@Component
public class ComplexMeasurementDao {

	@Autowired
	private Repository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public ComplexMeasurement get(Long id) {
		return repository.get(ComplexMeasurement.class, id);
	}

	public void delete(Long id) {
		repository.delete(ComplexMeasurement.class, id);
	}

	public List<ComplexMeasurement> list() {
		return repository.all(ComplexMeasurement.class);
	}

	public List<ComplexMeasurement> listForSensor(Long sensorId) {
		Criteria criteria = createCriteria(ComplexMeasurement.class);
		criteria.createAlias("sensor", "s").add(Restrictions.eq("s.id", sensorId));
		return list(criteria);
	}

	public List<ComplexMeasurement> listForResource(Long resourceId) {
		Criteria criteria = createCriteria(ComplexMeasurement.class);
		criteria.createAlias("sensor", "s").createAlias("s.resource", "sr")
				.add(Restrictions.eqOrIsNull("sr.id", resourceId));
		return list(criteria);
	}

	@SuppressWarnings("unchecked")
	private List<ComplexMeasurement> list(Criteria criteria) {
		return criteria.list();
	}

	private Criteria createCriteria(Class<?> clazz) {
		return sessionFactory.getCurrentSession().createCriteria(clazz);
	}

	public void save(ComplexMeasurement createCandidate) {
		repository.save(createCandidate);
	}
}
