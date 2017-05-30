package com.colptha.dom.entities.exceptions;

/**
 * Created by Colptha on 5/30/17.
 */
public class NegativeInventoryException extends IllegalArgumentException {
    public NegativeInventoryException() {}
    public NegativeInventoryException(String s) {super(s);}
    public NegativeInventoryException(String s, Throwable cause) {super(s, cause);}
    public NegativeInventoryException(Throwable cause) {super(cause);}
}
