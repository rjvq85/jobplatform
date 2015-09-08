package pt.criticalsoftware.publicplatform.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("pt.criticalsoftware.publicplatform.validators.NumberValidator")
public class NumberValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String valueString = String.valueOf(value);
		try {
			Integer nr = Integer.parseInt(valueString);
		} catch(NumberFormatException nfe) {
			FacesMessage msg = new FacesMessage(null,"Formato incorrecto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (String.valueOf(value).length() < 9) {
			FacesMessage msg = new FacesMessage(null,"Mínimo: 9 dígitos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
