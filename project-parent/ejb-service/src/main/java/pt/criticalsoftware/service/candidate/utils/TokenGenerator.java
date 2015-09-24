package pt.criticalsoftware.service.candidate.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TokenGenerator {
	
	private SecureRandom random;
	
	public TokenGenerator() {
		random = new SecureRandom();
	}
	
	public String getToken() {
		return new BigInteger(130,random).toString();
	}

}
