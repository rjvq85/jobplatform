package pt.criticalsoftware.service.model;

import java.time.LocalDate;

public interface IToken {

	String getEmail();

	void setEmail(String email);

	String getToken();

	void setToken(String token);

	LocalDate getExpiringDate();

	void setExpiringDate(LocalDate expiringDate);

}
