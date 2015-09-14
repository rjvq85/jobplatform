package pt.criticalsoftware.platform.report;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class report {

		private String[] selectedOptions;
	    private List<String> options;
	     
	    @PostConstruct
	    public void init() {
	    	options = new ArrayList<String>();
	    	options.add("Candidatos por período");
	    	options.add("Candidatos por posição");
	    	options.add("Candidaturas espôntaneas");
	    	options.add("Candidatos rejeitados");
	    	options.add("Resultados de entrevista");
	    	options.add("Propostas apresentadas");
	    	options.add("Tempo médio para primeira entrevista");
	    	options.add("Tempo médio para primeira contratação");
	    }

		public String[] getSelectedOptions() {
			return selectedOptions;
		}

		public void setSelectedOptions(String[] selectedOptions) {
			this.selectedOptions = selectedOptions;
		}

		public List<String> getOptions() {
			return options;
		}

		public void setOptions(List<String> options) {
			this.options = options;
		}
	    
	    
}
