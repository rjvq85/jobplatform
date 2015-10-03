package pt.criticalsoftware.domain.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.text.Position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.proxies.PositionProxy;
import pt.criticalsoftware.domain.service.PositionPersistenceService;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IPositionBuilder;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;



@RunWith(MockitoJUnitRunner.class)
public class PositionPersistenceServiceTest {

	@InjectMocks
	private PositionPersistenceService positionService;

	@EJB
	private IPositionBuilder positionBuilder;

	@Mock
	private EntityManager em;
	@Mock
	private Query query;


	@Test
	public void getPositionsByDateReturnsADate(){
		final String namedQuery = "Position.getPositionsByDate";
		
		LocalDate openDate= LocalDate.now();
		Date closeDate = Date.from(openDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println(closeDate);
		List<IPosition> entities=new ArrayList<IPosition>();
	}

}
