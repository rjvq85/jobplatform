package pt.criticalsoftware.service.timers;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ITokenBusinessService;
import pt.criticalsoftware.service.model.IToken;

@Stateless
public class TokenTimer {

	private static final Logger logger = LoggerFactory.getLogger(TokenTimer.class);

	@EJB
	private ITokenBusinessService bness;

	@Schedule(dayOfWeek = "Sun, Mon, Tue, Wed, Thu, Fri, Sat", hour = "00", minute = "00")
	public void expiredTokens() {
		List<IToken> tokens = bness.getAll();
		LocalDate nowDate = LocalDate.now();
		for (IToken token : tokens) {
			LocalDate expiringDate = token.getExpiringDate().plusDays(1);
			if (nowDate.isAfter(expiringDate))
				bness.remove(token.getToken());
		}

		logger.info("Foi realizada a limpeza de 'tokens' de anulação de password.");
	}

}
