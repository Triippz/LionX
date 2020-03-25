package edu.psu.lionx.interfaces;

import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;


/**
 * Basic Interface for Database I/O Operations, should be
 * implemented within each model or service.
 * @param <T>
 */
public interface IModelDatabase<T> {
    T save() throws ConstraintViolationException, LionxAuthenticationError, IOException;
    void delete();
}
