package pt.criticalsoftware.publicplatform.style;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.style.TextEJB;



@Named
@ApplicationScoped
public class TextBean {

	private final Logger logger = LoggerFactory.getLogger(TextBean.class);

	@EJB
	TextEJB ejbText;

	private String defaultText;
	private String selectedText, optionText;
	private List<String> texts;


	public TextBean() {

		this.defaultText = "Fundada em 1998, A CRITICAL Software é especializada no desenvolvimento de soluções de "
				+ "software e serviços de engenharia de informação, para o suporte de sistemas críticos orientados à "
				+ "segurança, missão e ao negócio de empresas. Ajudamos os nossos clientes a assegurar que os seus processos críticos "
				+ "de negócio são realizados de acordo com os mais exigentes padrões de qualidade no que respeita à segurança do "
				+ "software, ao desempenho e à fiabilidade. "
				+ "Os nossos produtos e serviços também fornecem aos clientes a informação necessária para a gestão eficiente e "
				+ "segura dos seus ativos importantes, ajudando-os a alcançar um melhor desempenho nos negócios.";
		this.selectedText=this.defaultText;
		this.optionText=this.defaultText;
		texts=new ArrayList <String>();
		texts.add("text1");
		texts.add("text2");
		texts.add("defaultText");
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public List<String> getTexts() {
		return texts;
	}

	public void setTexts(List<String> texts) {
		this.texts = texts;
	}

	public String getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public String getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(String selectedText) {
		if (selectedText.equals("text1"))
			this.optionText=" A CRITICAL Software apoia clientes em diversos mercados, incluindo empresas de elevado perfil tais como"
					+ ": AgustaWestland, EADS, ESA, NASA, Thales Alenia Space, as agências espaciais Chinesa e Japonesa, Vodafone, "
					+ "Deutsche Telekom, PT, Portucel-Soporcel, Infineon, EDP, Enersis, CGD, BCI, BFA, Unimed, AES, BPI, SIBS, mCel,"
					+ " UNITEL e Bank of New York. Renovámos a nossa certificação CMMI Nível 5 em Portugal e estendemo-la à nossa "
					+ "subsidiária no Brasil, seguida pela renovação das Certificações de Qualidade EN ISO 9001:2008 TickIT e EN "
					+ "9100 Revisão C. Fomos listados pela revista Business Week no Top 500 das empresas com mais rápido crescimento"
					+ " na Europa. Abrimos uma subsidiária na Alemanha, e lançámos outras spin-offs: CRITICAL Management Consulting,"
					+ " Watchful Software e verticalla. ";
		else 
			if (selectedText.equals("text2"))
				this.optionText="Na CRITICAL Software temos um sentimento de ética nos negócios, responsabilidade e compromisso com as "
						+ "decisões diárias e de cada negócio. Integridade, honestidade, solidariedade e transparência são valores "
						+ "importantes para todos os colaboradores da empresa. Acreditamos que a promoção de uma cultura de valores na "
						+ "empresa não é um obstáculo para o sucesso, mas uma garantia de excelência, rigor, competência e melhores práticas."
						+ "Na CRITICAL Software tratamos as pessoas como gostaríamos de ser tratados; valorizamos a diversidade e "
						+ "criamos um ambiente aberto e inclusivo, em que respeitamos as capacidades de todos os nossos colaboradores."
						+ " Acreditamos que a força do negócio vem da criação de um ambiente de trabalho inspirador, em que todos são "
						+ "compreendidos, valorizados e encorajados a dar o seu melhor, profissional e pessoalmente. "
						+ "Honramos o nosso Código de Ética nos Negócios, que expressa a nossa ética e determina as nossas relações com "
						+ "clientes, funcionários, fornecedores, autoridades governamentais, meios de comunicação, comunidades "
						+ "e com a sociedade. ";
			else 
				if (selectedText.equals("defaultText"))
					this.optionText=this.defaultText;
		this.selectedText = selectedText;
	}


}