package pt.criticalsoftware.platform.utils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class UserConverter implements Converter {

	@EJB
	private IUserBusinessService bness;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
            return null;
        }

        if (!value.matches("\\d+")) {
            throw new ConverterException("The value is not a valid ID number: " + value);
        }

        int id = Integer.parseInt(value);
        IUser user = bness.getUserByID(id);
		return user;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
            return null;
        }
		String id = String.valueOf(((IUser) value).getId());
		return id;
	}

}
