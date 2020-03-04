package edu.psu.lionx.interfaces;

import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import org.hibernate.exception.ConstraintViolationException;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Basic Interface for Database I/O Operations, should be
 * implemented within each model or service.
 * @param <T>
 */
public interface IModelDatabase<T> {
    T save() throws ConstraintViolationException, LionxAuthenticationError;
    T update();
    void delete();
}
