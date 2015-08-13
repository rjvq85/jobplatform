package pt.criticalsoftware.service.exceptions;

public class DuplicateReferenceException extends Exception{

	private static final long serialVersionUID = -7779658326839555286L;

	public DuplicateReferenceException() {
	}

	public DuplicateReferenceException(String message) {
		super(message);
	}
}
