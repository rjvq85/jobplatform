package pt.criticalsoftware.service.exceptions;

public class DuplicateTitleException extends Exception {

	private static final long serialVersionUID = 6051190637199929538L;

	public DuplicateTitleException() {
	}

	public DuplicateTitleException(String message) {
		super(message);
	}
}
