package pt.criticalsoftware.service.model;

import java.time.LocalDate;

import javax.resource.spi.IllegalStateException;

public interface INotification {

	LocalDate getDate();

	void setDate(LocalDate date);

	IUser getReceptor();

	void setReceptor(IUser receptor) throws IllegalStateException;

	String getSituation();

	void setSituation(String situation);

	String getText();

	void setText(String text);

	Integer getId();

}
