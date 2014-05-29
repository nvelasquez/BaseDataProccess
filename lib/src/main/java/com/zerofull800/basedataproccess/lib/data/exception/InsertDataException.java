package com.zerofull800.basedataproccess.lib.data.exception;

/**
 * Created by Novalogiq on 5/29/2014.
 */
public class InsertDataException extends BaseDataException {
    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public InsertDataException() {
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public InsertDataException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public InsertDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public InsertDataException(Throwable throwable) {
        super(throwable);
    }
}
