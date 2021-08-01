package dev.andrylat.carsharing.services;

import java.util.List;

public interface CrudService<T> {
    long getRecordsNumber();

    List<T> getAll(int pageNumber, int pageSize);

    T getById(long id);

    T add(T objectToAdd);

    boolean updateById(T updatedObject);

    boolean deleteById(long id);
}
