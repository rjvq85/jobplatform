package pt.criticalsoftware.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.criticalsoftware.rest.xml.InterviewXml;
import pt.criticalsoftware.rest.xml.InterviewsXml;
import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IInterview;

@RequestScoped
@Path("/interviews")
public class Interview {
	
	@EJB
	private IInterviewBusinessService bness;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
	@Path("/{id: \\d+}")
	public Response getInterviews(@PathParam("id") Integer id) {
		IInterview intrv = bness.getInterviewsById(id);
		InterviewXml interview = new InterviewXml(intrv);
		return Response.status(Status.OK).entity(interview).build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
	@Path("/interviewer/{id: \\d+}")
	public Response getInterviewsByInterviewer(@PathParam("id") Integer id) {
		List<IInterview> interviews = bness.getInterviewsByInterviewer(id);
		List<InterviewXml> xml = new ArrayList<>();
		interviews.stream().forEach(interview -> xml.add(new InterviewXml(interview)));
		InterviewsXml listInterviews = new InterviewsXml(xml);
		return Response.status(Status.OK).entity(listInterviews).build();
	}
}
