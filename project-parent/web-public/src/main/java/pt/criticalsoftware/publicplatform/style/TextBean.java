package pt.criticalsoftware.publicplatform.style;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IStyleBusinessService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TextBean implements Serializable  {

	private static final long serialVersionUID = 2898167443420554892L;
	
	@EJB
	IStyleBusinessService business;

	private String selectedText;
	private List<String> texts;
	private List<String> textsComplete;
	private String logoName;
	

	public TextBean() {
		texts=new ArrayList <String>();
		textsComplete=new ArrayList <String>();
//		textsComplete=business.getTextsComplete();
//		texts=business.getTexts();
//	
	}
	
	public String getSelectedText() {
//		String tt=business.getSelectedText();
//		int ind=0;
//		int i;
//		for(i=0; i<this.texts.size(); i++ )
//			if (this.texts.get(i).equals(tt))
//				ind=i;
//		this.selectedText=this.textsComplete.get(ind);
//		return	this.selectedText;
		return business.getSelectedText();
	}
	
	public void setSelectedText(String selectedText) {
		this.selectedText = selectedText;
	}
	public String getLogoName() {
		logoName=business.getSelecteLogo();
		if (logoName==null)
			logoName="logocritical.png";
		
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = "logocritical.png";
	}
}
