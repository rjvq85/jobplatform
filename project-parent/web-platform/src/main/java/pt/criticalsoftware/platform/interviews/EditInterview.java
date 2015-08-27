package pt.criticalsoftware.platform.interviews;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.model.IInterview;

@Named
@SessionScoped
public class EditInterview implements Serializable {

	private static final long serialVersionUID = 3088754897482942577L;
	
	private IInterview selectedInterview;

	public IInterview getSelectedInterview() {
		return selectedInterview;
	}

	public void setSelectedInterview(IInterview selectedInterview) {
		this.selectedInterview = selectedInterview;
	}
	
	
	
	

}
