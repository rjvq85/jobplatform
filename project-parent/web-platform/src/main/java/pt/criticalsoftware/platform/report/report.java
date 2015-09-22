package pt.criticalsoftware.platform.report;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@SessionScoped
public class Report implements Serializable {

	private static final long serialVersionUID = -7606230052846867853L;

	private final Logger logger = LoggerFactory.getLogger(Report.class);

	private void dialogShow(String dialogName){
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", true);
		options.put("contentHeight", 690);
		options.put("contentWith", 690);
		RequestContext.getCurrentInstance().openDialog(dialogName, options, null);
	}

	public void reportByTime(){
		dialogShow("viewReportCandidaciesByTime");
	}

	public void reportByPosition(){
		dialogShow("viewReportCandidaciesByPosition");
	}

	public void reportCandidacie(){
		dialogShow("viewReportCandidaciesSpontaneous");
	}

	public void reportNonAdmited(){
		dialogShow("viewReportNonAdmited");

	}	
	public void reportInterviews(){
		dialogShow("viewReportInterviews");

	}

	public void reportProposal(){
		dialogShow("viewReportProposal");

	}	

	public void reportFirstInterview(){
		dialogShow("viewReportFirstInterview");

	}

	public void reportFirstHiring(){
		dialogShow("viewReportFirstHiring");

	}

}