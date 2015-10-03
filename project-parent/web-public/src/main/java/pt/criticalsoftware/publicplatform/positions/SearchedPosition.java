package pt.criticalsoftware.publicplatform.positions;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;

@Named
@SessionScoped
public class SearchedPosition implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(SearchedPosition.class);

	private static final long serialVersionUID = 6210294118749872422L;
	@EJB
	private IPositionBusinessService positionBness;
	private Integer positionId;
	private IPosition selectedPosition;

	public void getPosition() {
		logger.debug("\n\n\n ### ID da posição: " + positionId + "\n\n\n");
		try {
			selectedPosition = positionBness.getPositionById(positionId);
		} catch (Exception e) {
			logger.error("Nenhuma posição encontrada.");
		}
	}

	public String testingPos(Integer id) {
		positionId = id;
		return "register";
	}

	public String goBack() {
		if (null != positionId)
			return null;
		else
			return "index";
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public IPosition getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(IPosition selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public String goOn() {
		if (null == positionId)
			return "jobs?faces-redirect=true";
		else
			return "searchedPosition?faces-redirect=true&ref=" + positionId;
	}

	public Boolean alreadyCandidate() {
		List<ICandidacy> candidacies = selectedPosition.getCandidacies();
		for (ICandidacy candidacy : candidacies) {
			if (candidacy.getCandidate().getId().equals(getUserID()))
				return true;
		}
		return false;
	}

	private Integer getUserID() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("userId");
	}

	public Boolean isOverdue() {
		LocalDate nowDate = LocalDate.now();
		Instant instant = Instant.ofEpochMilli(selectedPosition.getCloseDate().getTime());
		LocalDate positionDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		if (nowDate.isAfter(positionDate))
			return true;
		else
			return false;
	}

	public String getClosingTime() {
		Instant instant = Instant.ofEpochMilli(selectedPosition.getCloseDate().getTime());
		LocalDate positionDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		Integer day = positionDate.getDayOfMonth();
		Integer month = positionDate.getMonthValue();
		Integer year = positionDate.getYear();
		String mes = "";
		switch (month) {
		case 1:
			mes = "Janeiro";
			break;
		case 2:
			mes = "Fevereiro";
			break;
		case 3:
			mes = "Março";
			break;
		case 4:
			mes = "Abril";
			break;
		case 5:
			mes = "Maio";
			break;
		case 6:
			mes = "Junho";
			break;
		case 7:
			mes = "Julho";
			break;
		case 8:
			mes = "Agosto";
			break;
		case 9:
			mes = "Setembro";
			break;
		case 10:
			mes = "Outubro";
			break;
		case 11:
			mes = "Novembro";
			break;
		case 12:
			mes = "Dezembro";
			break;
		}
		String result = day + " do mês " + mes.toLowerCase() +" do ano " + year;
		return result;
	}

}
