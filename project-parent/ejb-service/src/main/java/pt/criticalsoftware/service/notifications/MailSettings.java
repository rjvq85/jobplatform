package pt.criticalsoftware.service.notifications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.ejb.Stateless;

@Stateless
public class MailSettings implements IMailSettings {
	
	private final String FILENAME = "mail.properties";
	private Map<String, String> settings;

	@Override
	public IMailSettings hostName(String hostName) {
		settings.put("host-name", hostName);
		return this;
	}
	
	@Override
	public IMailSettings smtpPort(String smtpPort) {
		settings.put("smtp-port", smtpPort);
		return this;
	}
	
	@Override
	public IMailSettings username(String username) {
		settings.put("username", username);
		return this;
	}
	
	@Override
	public IMailSettings password(String password) {
		settings.put("password", password);
		return this;
	}
	
	@Override
	public IMailSettings sslOnConnect(Boolean sslConnect) {
		settings.put("ssl-on-connect", sslConnect.toString());
		return this;
	}
	
	@Override
	public void update() {
		InputStream is = null;
		OutputStream os = null;
		Properties prop = new Properties();
		try {
			is = new FileInputStream(FILENAME);
			prop.load(is);
			for (Entry<String, String> entry:settings.entrySet()) {
				if (null != entry.getValue()) prop.replace(entry.getKey(), entry.getValue());
			}
			is.close();
			os = new FileOutputStream(FILENAME);
			prop.store(os, null);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
