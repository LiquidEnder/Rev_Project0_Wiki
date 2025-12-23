package org.example.service.Interface;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface <T, U>{
	Integer createEntity(T entity);
	Optional<T> getEntityById(Integer id);
	List<T> getAllEntities();
	T updateEntity(T newEntity);
	boolean deleteEntity(Integer id);

	// Conversion
	Optional<U> convertEntityToModel(T entity);

	Optional<U> getModelById(Integer id);

}
