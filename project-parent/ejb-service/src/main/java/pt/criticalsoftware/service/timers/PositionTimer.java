package pt.criticalsoftware.service.timers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.states.PositionState;

@Stateless
public class PositionTimer {

	private static final Logger logger = LoggerFactory.getLogger(PositionTimer.class);

	@EJB
	private ICandidateBusinessService candidateBness;
	@EJB
	private IPositionBusinessService positionBness;

	/**
	 * Checks every bussiness day at 00:01 if there are positions that are
	 * overdue. If true, then automatically closes that position
	 */
	@Schedule(dayOfWeek = "Mon,Tue,Wed,Thu,Fri", hour = "00", minute = "01")
	public void closedPositions() {
		int closedPositionsCounter = 0;
		LocalDate nowDate = LocalDate.now();
		List<IPosition> openPositions = positionBness.getOpenPositions();
		for (IPosition openPosition : openPositions) {
			Instant instant = Instant.ofEpochMilli(openPosition.getCloseDate().getTime());
			LocalDate positionCloseDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			if (nowDate.isAfter(positionCloseDate)) {
				openPosition.setState(PositionState.FECHADA);
				closedPositionsCounter++;
			}
		}

		if (closedPositionsCounter > 0)
			logger.info(closedPositionsCounter
					+ " posição/ões foi/foram fechada/s porque a sua data de encerramento expirou.");
	}

}
