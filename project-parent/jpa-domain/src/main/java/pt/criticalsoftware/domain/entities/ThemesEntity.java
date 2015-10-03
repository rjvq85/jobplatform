package pt.criticalsoftware.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "estilos")
@NamedQueries({ @NamedQuery(name = "ThemesEntity.getTheme", query = "SELECT u from ThemesEntity u") })

public class ThemesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public ThemesEntity(String selectedTT, String selectedTheme, String logoName) {
		this.selectedTT = selectedTT;
		this.selectedTheme = selectedTheme;
		this.selectedLogo = logoName;
	}

	public ThemesEntity() {
	}

	@Column(name = "Texto_seleccionado", columnDefinition = "TEXT")
	private String selectedTT;

	@Column(name = "tema_seleccionado")
	private String selectedTheme;

	@Column(name = "logo_seleccionado")
	private String selectedLogo;

	public String getSelectedLogo() {
		return selectedLogo;
	}

	public void setSelectedLogo(String selectedLogo) {
		this.selectedLogo = selectedLogo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSelectedTT() {
		return selectedTT;
	}

	public void setSelectedTT(String selectedTT) {
		this.selectedTT = selectedTT;
	}

	public String getSelectedTheme() {
		return selectedTheme;
	}

	public void setSelectedTheme(String selectedTheme) {
		this.selectedTheme = selectedTheme;
	}

}
