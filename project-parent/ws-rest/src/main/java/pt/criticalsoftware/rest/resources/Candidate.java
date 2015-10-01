package pt.criticalsoftware.rest.resources;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.criticalsoftware.rest.xml.CandidateXml;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

@RequestScoped
@Path("/candidate")
public class Candidate {

	@EJB
	private ICandidateBusinessService business;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	@Path("/{id: \\d+}")
	public Response candidateDetails(@PathParam("id") Integer id) {
		try {
			ICandidate candidate = business.getCandidateById(id);
			CandidateXml xml = new CandidateXml(candidate);
			return Response.status(Status.OK).entity(xml).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
