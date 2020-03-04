package edu.psu.lionx.repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IModelRepository<T> {
    List<T> findAll() throws Exception;
    Optional<T> find(@NotNull T obj);
    List<T> find (String queryName, String[] params, Object[] bindValues);
}
