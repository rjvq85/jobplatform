package pt.criticalsoftware.service.exceptions;

public class UniqueConstraintException extends Exception {

	private static final long serialVersionUID = 1L;

	public UniqueConstraintException() {
	}

	public UniqueConstraintException(String message) {
		super(message);
	}

}
