package edu.psu.lionx.Exceptions;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

public class LionXConstraintException  extends ConstraintViolationException {
    private String constraintColumn;

    public LionXConstraintException(String message, SQLException root, String constraintName) {
        super(message, root, constraintName);
    }
    public LionXConstraintException(String message, SQLException root, String constraintName, String constraintColumn) {
        super(message, root, constraintName);
        this.constraintColumn = constraintColumn;
    }

    public String getConstraintColumn() {
        return constraintColumn;
    }
}
