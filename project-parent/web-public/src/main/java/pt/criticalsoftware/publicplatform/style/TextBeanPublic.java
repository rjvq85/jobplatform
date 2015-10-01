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
public class TextBeanPublic implements Serializable  {

	private static final long serialVersionUID = 2898167443420554892L;

	@EJB
	IStyleBusinessService business;

	private String selectedText;
	private List<String> texts;
	private List<String> textsComplete;
	private String logoName;


	public TextBeanPublic() {
		texts=new ArrayList <String>();
		textsComplete=new ArrayList <String>();
		//		textsComplete=business.getTextsComplete();
		//		texts=business.getTexts();
		//	
	}

	public String getSelectedText() {
		String tt=business.getSelectedText();
		if (tt==null)
			tt="Fundada em 1998, A CRITICAL Software é especializada no desenvolvimento de soluções de "
					+ "software e serviços de engenharia de informação, para o suporte de sistemas críticos orientados à "
					+ "segurança, missão e ao negócio de empresas. Ajudamos os nossos clientes a assegurar que os seus processos críticos "
					+ "de negócio são realizados de acordo com os mais exigentes padrões de qualidade no que respeita à segurança do "
					+ "software, ao desempenho e à fiabilidade. "
					+ "Os nossos produtos e serviços também fornecem aos clientes a informação necessária para a gestão eficiente e "
					+ "segura dos seus ativos importantes, ajudando-os a alcançar um melhor desempenho nos negócios.";

		return tt;
		
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
