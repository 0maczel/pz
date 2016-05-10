package pz.monitor.service.common;

import pz.monitor.db.entity.Entity;

public interface DtoConverter<E extends Entity, D extends Dto> {
	D toDto(E entity);
	E toEntity(D dto);
}
