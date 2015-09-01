package pt.criticalsoftware.rest.resources;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

@RequestScoped
@Path("/candidate")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public class Candidate {
	
	@EJB
	private ICandidateBusinessService business;
	
	@GET
	@Path("/{id: \\d+}")
	public Object candidateDetails(@PathParam("id") Integer id) {
		try {
			ICandidate candidate = business.getCandidateById(id);
			return candidate;
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Candidato não encontrado ou endereço incorrecto").build();
		}
	}
}
