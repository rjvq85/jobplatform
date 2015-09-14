package pt.criticalsoftware.service.scriptxml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLParser {
	
	public String asString(Object object) {
		StringWriter sw = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
				Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(object, sw);
				return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ScriptListXML asXML(String str) {
		InputStream is = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
		try {
			JAXBContext context = JAXBContext.newInstance(ScriptListXML.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			ScriptListXML scripts = (ScriptListXML) unmarshaller.unmarshal(is);
			return scripts;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
