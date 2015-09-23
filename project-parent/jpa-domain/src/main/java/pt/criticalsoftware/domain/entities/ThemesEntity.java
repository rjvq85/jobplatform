package pt.criticalsoftware.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "estilos")
@NamedQueries({ @NamedQuery(name = "ThemesEntity.getTheme", query = "SELECT u from ThemesEntity u")
})

public class ThemesEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	public ThemesEntity(List<String> texts2, List<String> textsComplete2,
//			String selectedTT, String selectedTheme) {
//		this.texts = texts2;
//		this.textsComplete = textsComplete2;
//		this.selectedTT = selectedTT;
//		this.selectedTheme = selectedTheme;
//	}
	public ThemesEntity(String selectedTT, String selectedTheme, String logoName) {
		this.selectedTT = selectedTT;
		this.selectedTheme = selectedTheme;
		this.selectedLogo=logoName;
	}
	public ThemesEntity() {
	}
		
//	@ElementCollection
//	@Column(name = "nomes_textos")
//	private List<String> texts;
//	
//	@ElementCollection
//	@Column(name = "textos_apresentacao",columnDefinition="TEXT", length = 65535)
//	private List<String> textsComplete;

	@Column(name="Texto_seleccionado", columnDefinition="TEXT")
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
//	public List<String> getTexts() {
//		return texts;
//	}
//	public void setTexts(List<String> texts) {
//		this.texts = texts;
//	}
//	public List<String> getTextsComplete() {
//		return textsComplete;
//	}
//	public void setTextsComplete(List<String> textsComplete) {
//		this.textsComplete = textsComplete;
//	}
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
