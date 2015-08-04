package pt.criticalsoftware.platform.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("pt.criticalsoftware.platform.validators.PasswordValidator")
public class PasswordValidator implements Validator {
	
	private static final String PATTERN = "^[a-zA-Z0-9]*$";
	private Pattern pattern;
	private Matcher matcher;
	
 	public PasswordValidator() {
 		pattern = Pattern.compile(PATTERN);
	}
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			FacesMessage message = new FacesMessage(null, "Caractere(s) incorrecto(s)");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} else if (value.toString().length() < 6) {
			FacesMessage message = new FacesMessage(null, "Password: mín. 6 caracteres");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}
