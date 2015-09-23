package pt.criticalsoftware.publicplatform.style;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IStyleBusinessService;

import java.io.Serializable;

@Named
@SessionScoped
public class Tt implements Serializable {

	private static final long serialVersionUID = 2898167443420554892L;
	
	@EJB
	IStyleBusinessService business;

	private String selectedText;
	
	public String getSelectedText() {
		return	business.getSelectedText();
		
	}
	public void setSelectedText(String selectedText) {
		this.selectedText = selectedText;
	}
	public Tt() {
		
	}
	

}
