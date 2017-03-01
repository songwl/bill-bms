package com.yipeng.bill.bms.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务异常，这个异常可在service层抛出，在生产环境去掉异常栈
 *
 * Created by lancey on 15/2/5.
 */
public class BussinessException extends RuntimeException {

	private static final Logger LOGGER = LoggerFactory.getLogger(BussinessException.class);

	private int code;

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public BussinessException(String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this runtime exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 * @since 1.4
	 */
	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussinessException(int code) {
		this("error,code:" + code);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	/**
	 * Fills in the execution stack trace. This method records within this
	 * {@code Throwable} object information about the current state of
	 * the stack frames for the current thread.
	 * <p/>
	 * <p>If the stack trace of this {@code Throwable} {@linkplain
	 * Throwable#Throwable(String, Throwable, boolean, boolean) is not
	 * writable}, calling this method has no effect.
	 *
	 * @return a reference to this {@code Throwable} instance.
	 * @see Throwable#printStackTrace()
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		if (LOGGER.isDebugEnabled()) {
			return super.fillInStackTrace();
		} else {
			Throwable throwable = new Throwable(this.getMessage());
			return throwable;
		}
	}
}
