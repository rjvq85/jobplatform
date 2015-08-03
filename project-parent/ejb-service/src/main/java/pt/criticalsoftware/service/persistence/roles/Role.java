package pt.criticalsoftware.service.persistence.roles;

public enum Role {
	
	ADMIN,
	ENTREVISTADOR,
	GESTOR;
	
	public String getRole() {
		return this.toString();
	}

}
