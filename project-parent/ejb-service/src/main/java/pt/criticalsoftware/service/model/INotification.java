package pt.criticalsoftware.service.model;

import java.time.LocalDate;

public interface INotification {

	LocalDate getDate();

	void setDate(LocalDate date);

	IUser getReceptor();

	void setReceptor(IUser receptor);

	String getSituation();

	void setSituation(String situation);

	String getText();

	void setText(String text);

	Integer getId();

}
