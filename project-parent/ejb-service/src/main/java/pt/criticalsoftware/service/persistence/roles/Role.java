package pt.criticalsoftware.service.persistence.roles;

public enum Role {

	ADMIN("Administrador"), ENTREVISTADOR("Entrevistador"), GESTOR("Gestor");

	private String detail;

	private Role(String detail) {
		this.detail = detail;
	}

	public String getRole() {
		return this.toString();
	}

	public String getDetail() {
		return detail;
	}

}
