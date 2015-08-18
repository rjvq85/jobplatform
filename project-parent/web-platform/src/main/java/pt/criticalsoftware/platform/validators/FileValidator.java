package pt.criticalsoftware.platform.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("pt.criticalsoftware.platform.validators.FileValidator")
public class FileValidator implements Validator {

	public FileValidator() {
	}



	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Part file = (Part) value;
		String filename = file.getSubmittedFileName();
		
		String[] splt = filename.split("\\.");
		
		String extension = splt[splt.length-1];
		
		if (extension == null) return;
	
		if (!file.getContentType().equals("application/pdf")) {
			FacesMessage msg = new FacesMessage("Ficheiro", 
					"Formato não suportado.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			throw new ValidatorException(msg);
		}
		
		if (file.getSize() > 2097152) { // Max. File size: 2MB
			FacesMessage msg = new FacesMessage("Ficheiro", 
					"Tamanho superior ao limite (2MB)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			throw new ValidatorException(msg);
		}
		
	}



}

