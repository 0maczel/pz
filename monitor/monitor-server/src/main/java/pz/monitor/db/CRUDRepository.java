package pz.monitor.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Entity;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryConverter;

@Component
public class CRUDRepository implements Repository {
	private SessionFactory sessionFactory;
	private QueryConverter<Criteria> queryConverter;

	@Autowired
	public CRUDRepository(SessionFactory sessionFactory, QueryConverter<Criteria> queryConverter) {
		this.sessionFactory = sessionFactory;
		this.queryConverter = queryConverter;
	}

	@Override
	public <T extends Entity> List<T> all(Class<T> type) {
		@SuppressWarnings("unchecked")
		List<T> resultList = createCriteria(type).list();
		return resultList;
	}

	@Override
	public <T extends Entity> T get(Class<T> type, Long id) {
		return getSession().get(type, id);
	}

	@Override
	public <T extends Entity> void save(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public <T extends Entity> void delete(T entity) {
		getSession().delete(entity);
	}
	
	@Override
	public <T extends Entity> List<T> query(Query<T> query) {
		Criteria criteria = createCriteria(query.getType());
		@SuppressWarnings("unchecked")
		List<T> resultList = queryConverter.convert(query, criteria).list();
		return resultList;
	}
	
	@Override
	public <T extends Entity> Number count(Class<T> type) {
		return (Number) createCriteria(type).setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * Get current thread session.
	 * 
	 * @return hibernate session
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Create criteria based on entity class type.
	 * 
	 * @param clazz
	 *            entity class
	 * @return criteria
	 */
	private <T extends Entity> Criteria createCriteria(Class<T> clazz) {
		return sessionFactory.getCurrentSession().createCriteria(clazz);
	}
}
