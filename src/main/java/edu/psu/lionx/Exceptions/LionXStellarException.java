package edu.psu.lionx.Exceptions;

public class LionXStellarException extends Exception {
    public LionXStellarException(String msg) {
        super(msg);
    }
    public LionXStellarException(String msg, Exception e) {
        super(msg, e);
    }
}
