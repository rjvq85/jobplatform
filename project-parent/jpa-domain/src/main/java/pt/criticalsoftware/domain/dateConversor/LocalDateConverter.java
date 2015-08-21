package pt.criticalsoftware.domain.dateConversor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate date) {
	if (date == null) {
	return null;
	}
	final Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
	return Date.from(instant);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date value) {
	if (value == null) {
	return null;
	}
	final Instant instant = Instant.ofEpochMilli(value.getTime());
	return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

}

