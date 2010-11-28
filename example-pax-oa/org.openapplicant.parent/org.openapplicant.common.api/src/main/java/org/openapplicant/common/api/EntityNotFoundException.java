package org.openapplicant.common.api;

public class EntityNotFoundException extends Exception {
	
	private static final long serialVersionUID = -1781203632104727433L;

	public EntityNotFoundException() {
		super();
	}
	
	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
