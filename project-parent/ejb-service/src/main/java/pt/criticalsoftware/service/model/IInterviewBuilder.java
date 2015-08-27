package pt.criticalsoftware.service.model;

import java.time.LocalDate;
import java.util.List;

public interface IInterviewBuilder {

	IInterview build();

	IInterviewBuilder date(LocalDate date);

	IInterviewBuilder feedback(String feedback);

	IInterviewBuilder interviewers(List<IUser> interviewers);

	IInterviewBuilder script(IScript script);

	IInterviewBuilder position(IPosition pos);

	IInterviewBuilder candidacy(ICandidacy cand);

}
