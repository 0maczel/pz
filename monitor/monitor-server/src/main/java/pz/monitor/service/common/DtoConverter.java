package pz.monitor.service.common;

import java.util.List;
import java.util.stream.Collectors;

import pz.monitor.db.entity.Entity;

public interface DtoConverter<E extends Entity, D extends Dto> {
	D toDto(E entity);

	E toEntity(D dto);

	default List<D> covertListToDto(List<E> entityList) {
		return entityList.stream().map(this::toDto).collect(Collectors.toList());
	}
}
