package edu.psu.lionx.interfaces;

import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import org.hibernate.exception.ConstraintViolationException;


/**
 * Basic Interface for Database I/O Operations, should be
 * implemented within each model or service.
 * @param <T>
 */
public interface IModelDatabase<T> {
    T save() throws ConstraintViolationException, LionxAuthenticationError;
    void delete();
}
