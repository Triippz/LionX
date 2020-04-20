package edu.psu.lionx.Exceptions;

import javassist.NotFoundException;

public class LionXEmptyEnvException extends NotFoundException {
    public LionXEmptyEnvException(String msg) {
        super(msg);
    }
    public LionXEmptyEnvException(String msg, Exception e) {
        super(msg, e);
    }
}
