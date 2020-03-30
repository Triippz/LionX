package edu.psu.lionx.Exceptions;

public class LionXUserNotFoundException extends Exception {
    public LionXUserNotFoundException(String msg) {
        super(msg);
    }
    public LionXUserNotFoundException(String msg, Exception e) {
        super(msg, e);
    }
}
