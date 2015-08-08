package pt.criticalsoftware.service.model.dtos;

import java.time.LocalDate;
import java.util.Collection;
import pt.criticalsoftware.service.persistence.states.PositionState;

public class PositionDTO {

	private Integer id;
	private LocalDate openDate;
	private LocalDate closeDate;
	private String reference;
	private String title;
	private String locale;
	private PositionState state;
	private String company;
	private String technicalArea;
	private String sla;
	private Integer vacancies;
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getOpenDate() {
		return openDate;
	}
	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}
	public LocalDate getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public PositionState getState() {
		return state;
	}
	public void setState(PositionState state) {
		this.state = state;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTechnicalArea() {
		return technicalArea;
	}
	public void setTechnicalArea(String technicalArea) {
		this.technicalArea = technicalArea;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
	public Integer getVacancies() {
		return vacancies;
	}
	public void setVacancies(Integer vacancies) {
		this.vacancies = vacancies;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection<String> getAdChannels() {
		return adChannels;
	}
	public void setAdChannels(Collection<String> adChannels) {
		this.adChannels = adChannels;
	}
	private Collection<String> adChannels;
}
