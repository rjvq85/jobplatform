package pt.criticalsoftware.domain.proxies;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.sun.syndication.io.impl.Base64;

import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.service.model.ICandidate;

public class CandidateProxy implements ICandidate, IEntityAware<CandidateEntity> {

	private CandidateEntity candidate;

	@Override
	@XmlTransient
	public CandidateEntity getEntity() {
		return candidate;
	}

	public CandidateProxy() {
		this(null);
	}

	public CandidateProxy(CandidateEntity entity) {
		candidate = entity != null ? entity : new CandidateEntity();
	}

	@Override
	public Integer getId() {
		return candidate.getId();
	}

	@Override
	public String getUsername() {
		return candidate.getUsername();
	}

	@Override
	public void setUsername(String username) {
		candidate.setUsername(username);
	}

	@Override
	public String getPassword() {
		return candidate.getPassword();
	}

	@Override
	public void setPassword(String password) {
		candidate.setPassword(password);
	}

	@Override
	public String getEmail() {
		return candidate.getEmail();
	}

	@Override
	public void setEmail(String email) {
		candidate.setEmail(email);
	}

	@Override
	public String getFirstName() {
		return candidate.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		candidate.setFirstName(firstName);
	}

	@Override
	public String getLastName() {
		return candidate.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		candidate.setLastName(lastName);
	}

	@Override
	public String getAddress() {
		return candidate.getAddress();
	}

	@Override
	public void setAddress(String address) {
		candidate.setAddress(address);
	}

	@Override
	public String getTown() {
		return candidate.getTown();
	}

	@Override
	public void setTown(String town) {
		candidate.setTown(town);
	}

	@Override
	public String getCountry() {
		return candidate.getCountry();
	}

	@Override
	public void setCountry(String country) {
		candidate.setCountry(country);
	}

	@Override
	public Integer getPhone() {
		return candidate.getPhone();
	}

	@Override
	public void setPhone(Integer phone) {
		candidate.setPhone(phone);
	}

	@Override
	public Integer getMobilePhone() {
		return candidate.getMobilePhone();
	}

	@Override
	public void setMobilePhone(Integer mobilePhone) {
		candidate.setMobilePhone(mobilePhone);
	}

	@Override
	public List<String> getCourse() {
		return (List<String>) candidate.getCourse();
	}

	@Override
	public void setCourse(Collection<String> course) {
		candidate.setCourse(course);
	}

	@Override
	public Collection<String> getDegree() {
		return candidate.getDegree();
	}

	@Override
	public void setDegree(Collection<String> degree) {
		candidate.setDegree(degree);
	}

	@Override
	public Collection<String> getUniversity() {
		return candidate.getUniversity();
	}

	@Override
	public void setUniversity(Collection<String> university) {
		candidate.setUniversity(university);
	}

	@Override
	public String getCv() {
		return candidate.getCv();
	}

	@Override
	public void setCv(String cv) {
		candidate.setCv(cv);
	}

	@Override
	public String toString() {
		return candidate.getFirstName() + " " + candidate.getLastName();
	}

}
