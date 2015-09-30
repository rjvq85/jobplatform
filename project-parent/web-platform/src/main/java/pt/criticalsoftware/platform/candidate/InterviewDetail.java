package pt.criticalsoftware.platform.candidate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
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

	private List<InterviewerSet> interviewers;

	private IInterview interview;
	private Map<String, String> feedback;

	public IInterview getInterview() {
		return interview;
	}

	public void setInterview(IInterview interview) {
		interviewers = new ArrayList<>();
		this.interview = interview;
		feedback = (null != feedback) ? feedback : new HashMap<>();
		try {
			XMLParser parser = new XMLParser();
			ScriptListXML feedbacks = (ScriptListXML) parser.asXML(interview.getFeedback());
			for (Map.Entry<Integer, ScriptXML> fb : feedbacks.getScripts().entrySet()) {
				IUser u = userBness.getUserByID(fb.getKey());
				String uName = u.getFirstName() + " " + u.getLastName();
				InterviewerSet set = new InterviewerSet();
				set.setInterviewer(uName);
				for (Map.Entry<String, String> mEntry : fb.getValue().getQuestionsAndAnswers().entrySet()) {
					set.getQuestions().add(mEntry.getKey());
					set.getAnswers().add(mEntry.getValue());
				}
				set.setFinalComment(fb.getValue().getFinalComment());
				interviewers.add(set);
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

	public List<InterviewerSet> getInterviewers() {
		return interviewers;
	}

	public void setInterviewers(List<InterviewerSet> interviewers) {
		this.interviewers = interviewers;
	}

	public class InterviewerSet {
		private String interviewer;
		private List<String> questions;
		private List<String> answers;
		private String finalComment;

		public InterviewerSet() {
			interviewer = "";
			questions = new ArrayList<>();
			answers = new ArrayList<>();
			finalComment = "";
		}

		public String getInterviewer() {
			return interviewer;
		}

		public void setInterviewer(String interviewer) {
			this.interviewer = interviewer;
		}

		public List<String> getQuestions() {
			return questions;
		}

		public void setQuestions(List<String> questions) {
			this.questions = questions;
		}

		public List<String> getAnswers() {
			return answers;
		}

		public void setAnswers(List<String> answers) {
			this.answers = answers;
		}

		public String getFinalComment() {
			return finalComment;
		}

		public void setFinalComment(String finalComment) {
			this.finalComment = finalComment;
		}

	}


}
