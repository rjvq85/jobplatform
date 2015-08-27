package pt.criticalsoftware.domain.proxies;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.ejb.Stateless;

import com.sun.syndication.io.impl.Base64;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;

@Stateless
public class CandidateBuilder implements ICandidateBuilder {

	private CandidateProxy candidate;

	public CandidateBuilder() {
		candidate = new CandidateProxy();
	}

	@Override
	public ICandidateBuilder firstName(String fn) {
		candidate.setFirstName(fn);
		return this;
	}

	@Override
	public ICandidateBuilder lastName(String lastName) {
		candidate.setLastName(lastName);
		return this;
	}

	@Override
	public ICandidateBuilder email(String email) {
		candidate.setEmail(email);
		return this;
	}

	@Override
	public ICandidateBuilder password(String password) {
		String encryptPass;
		try {
			encryptPass = encryptPass(password);
			candidate.setPassword(encryptPass);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// log.error("Password encrypt error");
		}
		return this;
	}

	@Override
	public ICandidateBuilder username(String username) {
		candidate.setUsername(username);
		return this;
	}

	@Override
	public ICandidateBuilder address(String address) {
		candidate.setAddress(address);
		return this;
	}

	@Override
	public ICandidateBuilder town(String town) {
		candidate.setTown(town);
		return this;
	}

	@Override
	public ICandidateBuilder country(String country) {
		candidate.setCountry(country);
		return this;
	}

	@Override
	public ICandidateBuilder phone(Integer phone) {
		candidate.setPhone(phone);
		return this;
	}

	@Override
	public ICandidateBuilder mobile(Integer mobile) {
		candidate.setMobilePhone(mobile);
		return this;
	}

	@Override
	public ICandidateBuilder course(String course) {
		if (null == candidate.getCourse())
			candidate.setCourse(new ArrayList<>());
		candidate.getCourse().add(course);
		return this;
	}

	@Override
	public ICandidateBuilder degree(String degree) {
		if (null == candidate.getDegree())
			candidate.setDegree(new ArrayList<>());
		candidate.getDegree().add(degree);
		return this;
	}

	@Override
	public ICandidateBuilder school(String school) {
		if (null == candidate.getUniversity())
			candidate.setUniversity(new ArrayList<>());
		candidate.getUniversity().add(school);
		return this;
	}

	@Override
	public ICandidateBuilder cv(String cvPath) {
		candidate.setCv(cvPath);
		return this;
	}

	@Override
	public ICandidate build() {
		return candidate;
	}

	private String encryptPass(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String securedPassword = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pass.getBytes());

			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			securedPassword = new String(data2);
			return securedPassword;
		} catch (NoSuchAlgorithmException e) {
		}

		return securedPassword;
	}

}
