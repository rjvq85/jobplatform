package pt.criticalsoftware.publicplatform.access.utils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Named
@RequestScoped
public class FileUploadPublic implements Serializable {
	

	private static final long serialVersionUID = 9164322367643334448L;
	private String path;
	private Part file;

	public String fileUpload(String username) throws IOException { // 'String
																	// username'
																	// is the
																	// username
																	// of the
																	// person
																	// whose CV
																	// is being
																	// uploaded
		path = System.getProperty("user.dir");
		File folder = new File(path + "/cvs/");
		String fileName = username.concat("_").concat(getFilename(file));
		InputStream inputStream = file.getInputStream();
		if (folder.exists()) {
			FileOutputStream outputStream = new FileOutputStream(new File(folder, fileName));
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			while (true) {
				bytesRead = inputStream.read(buffer);
				if (bytesRead > 0) {
					outputStream.write(buffer, 0, bytesRead);
				} else {
					break;
				}
			}
			outputStream.close();
			inputStream.close();
		} else {
			folder.mkdir();
			FileOutputStream outputStream = new FileOutputStream(new File(folder, fileName));
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			while (true) {
				bytesRead = inputStream.read(buffer);
				if (bytesRead > 0) {
					outputStream.write(buffer, 0, bytesRead);
				} else {
					break;
				}
			}
			outputStream.close();
			inputStream.close();
		}
		String finalPath = folder.getAbsolutePath() + "/" + fileName;
		return finalPath;

	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return "";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
