package pt.criticalsoftware.service.timers;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;

@Stateless
public class Timer {
	
	private static final Logger logger = LoggerFactory.getLogger(Timer.class);

	@EJB
	private ICandidateBusinessService candidateBness;
	@EJB
	private IPositionBusinessService positionBness;
	
	@Schedule(dayOfWeek = "Thu", hour = "20", minute = "48")
	public void openPosition() {
		List<IPosition> positions = positionBness.getOpenPositions();
		logger.error((LocalDateTime.now() + " - Timer activado!"));
	}
	
}
