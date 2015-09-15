package pt.criticalsoftware.platform.candidate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.scriptxml.ScriptListXML;
import pt.criticalsoftware.service.scriptxml.ScriptXML;
import pt.criticalsoftware.service.scriptxml.XMLParser;

@Named
@SessionScoped
public class InterviewDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUserBusinessService userBness;
	
	private IInterview interview;
	private Map<String,String> feedback;

	public IInterview getInterview() {
		return interview;
	}

	public void setInterview(IInterview interview) {
		System.out.println("\n\n DEFINIU A ENTREVISTA: " + interview.getReference() + "\n\n");
		this.interview = interview;
		feedback = (null != feedback) ? feedback : new HashMap<>();
		try {
			XMLParser parser = new XMLParser();
			ScriptListXML feedbacks = (ScriptListXML) parser.asXML(interview.getFeedback());
			for (Map.Entry<Integer, ScriptXML> fb : feedbacks.getScripts().entrySet()) {
				IUser u = userBness.getUserByID(fb.getKey());
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, String> mEntry : fb.getValue().getQuestionsAndAnswers().entrySet()) {
					sb.append("P: " + mEntry.getKey() + "\nR: " + mEntry.getValue() + "\n");
				}
				sb.append("\nAvaliação Final: " + fb.getValue().getFinalComment());
				feedback.put(u.getFirstName() + " " + u.getLastName(), sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao passar feedback");
		}
	}

	public Map<String, String> getFeedback() {
		return feedback;
	}

	public void setFeedback(Map<String, String> feedback) {
		this.feedback = feedback;
	}
	
	public List<Map.Entry<String, String>> getFeedbacks() {
		Set<Map.Entry<String, String>> result = feedback.entrySet();
		return new ArrayList<>(result);
	}
	
	
	
	

}
