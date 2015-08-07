package pt.criticalsoftware.service.exceptions;

public class DuplicateEmailException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException() {
	}

	public DuplicateEmailException(String message) {
		super(message);
	}

}
