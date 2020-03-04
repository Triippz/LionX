package edu.psu.lionx.Exceptions;

import javassist.NotFoundException;

public class LionxAuthenticationError extends NotFoundException {
    public LionxAuthenticationError(String msg) {
        super(msg);
    }
    public LionxAuthenticationError(String msg, Exception e) {
        super(msg, e);
    }
}
