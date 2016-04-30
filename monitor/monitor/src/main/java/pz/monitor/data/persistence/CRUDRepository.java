package pz.monitor.data.persistence;

import java.util.List;

import org.hibernate.Session;

import pz.monitor.data.model.Entity;

public class CRUDRepository implements Repository {
	Session session;
	
	public CRUDRepository(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> List<T> all(Class<T> type) {
		return (List<T>)session.createCriteria(type).list();
	}
	
	@Override
	public <T extends Entity> T get(Class<T> type, int id) {
		return session.get(type, id);
	}

	@Override
	public <T extends Entity> void save(T entity) {
		session.saveOrUpdate(entity);
	}

	@Override
	public <T extends Entity> void delete(T entity) {
		session.delete(entity);
	}
}
