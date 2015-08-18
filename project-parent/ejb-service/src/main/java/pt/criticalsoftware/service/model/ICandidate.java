package pt.criticalsoftware.service.model;

import java.util.Collection;

public interface ICandidate {

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	String getAddress();

	void setAddress(String address);

	String getTown();

	void setTown(String town);

	String getCountry();

	void setCountry(String country);

	Integer getPhone();

	void setPhone(Integer phone);

	Integer getMobilePhone();

	void setMobilePhone(Integer mobilePhone);

	Collection<String> getCourse();

	void setCourse(Collection<String> course);

	Collection<String> getDegree();

	void setDegree(Collection<String> degree);

	Collection<String> getUniversity();

	void setUniversity(Collection<String> university);

	String getCv();

	void setCv(String cv);

	Integer getId();

}
