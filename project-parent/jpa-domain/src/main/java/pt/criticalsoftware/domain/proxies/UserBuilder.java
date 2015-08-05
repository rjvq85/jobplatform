package pt.criticalsoftware.domain.proxies;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;

import com.sun.syndication.io.impl.Base64;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.roles.Role;

@RequestScoped
public class UserBuilder implements IUserBuilder {
	
	private UserProxy user;
	
	public UserBuilder() {
		user = new UserProxy();
	}
	
	@Override
	public IUserBuilder firstName(String name) {
		user.setFirstName(name);
		return this;
	}
	
	@Override
	public IUserBuilder lastName(String name) {
		user.setLastName(name);
		return this;
	}
	
	@Override
	public IUserBuilder email(String email) {
		user.setEmail(email);
		return this;
	}
	
	private String encryptPass(String pass) throws NoSuchAlgorithmException,
	UnsupportedEncodingException {
		String securedPassword = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pass.getBytes());

			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			securedPassword = new String(data2);
			return securedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return securedPassword;
	}
	
	@Override
	public IUserBuilder password(String password) {
		String encryptPass;
		try {
			encryptPass = encryptPass(password);
			user.setPassword(encryptPass);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			//log.error("Password encrypt error");
			e.printStackTrace();
		}
		
		return this;
	}
	
	@Override
	public IUserBuilder username(String username) {
		user.setUsername(username);
		return this;
	}
	
	@Override
	public IUserBuilder role(Role role) {
		if (null == user.getRoles()) user.setRoles(new ArrayList<Role>());
		user.getRoles().add(role);
		return this;
	}
	
	@Override
	public IUser build() {
		return user;
	}

}
