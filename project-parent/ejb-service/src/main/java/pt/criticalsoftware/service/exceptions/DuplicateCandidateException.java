package pt.criticalsoftware.service.exceptions;

public class DuplicateCandidateException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateCandidateException() {
	}

	public DuplicateCandidateException(String message) {
		super(message);
	}

}
