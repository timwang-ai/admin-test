package com.tim.exception;

import java.io.IOException;

/**
 * @ Author  : Tim Wang
 * @ FileName: BrowserNameException.java
 * @ Time    : 2020/11/9 10:57
 */
public class BrowserNameException extends RuntimeException {
    public BrowserNameException() {
        super();
    }

    public BrowserNameException(String s) {
        super(s);
    }
}
