package org.openapplicant.common.api;

public class InvalidEntityException extends Exception {

	private static final long serialVersionUID = 6931539817028902916L;
	
	public InvalidEntityException(String msg) {
		super(msg);
	}
	
	public InvalidEntityException() {
		super();
	}

}
