package org.simu.dd5.simulateur.domaine.opposant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.attaque.AttaqueJson;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpposantJson {
	@JsonProperty("creature")
	private String nomCreature;
	private String ca;

	@JsonProperty("points_de_vie")
	private String pointsDeVie;
	private String vitesse;

	private String force;
	private String dexterite;
	private String constitution;
	private String intelligence;
	private String sagesse;
	private String charisme;

	private String competences;

	@JsonProperty("jets_sauvegarde")
	private String jetsSauvegarde;

	@JsonProperty("immunites_aux_degats")
	private String immunitesAuxDegats;
	@JsonProperty("immunites_aux_etats")
	private String immunitesAuxEtats;
	@JsonProperty("resistances_aux_degats")
	private String resistancesAuxDegats;
	@JsonProperty("vulnerabilites_aux_degats")
	private String vulnerabilitesAuxDegats;

	private String dangerosite;
	@JsonProperty("bonus_de_maitrise")
	private String bonusDeMaitrise;

	//@JsonIgnore
	@JsonProperty("liste-attaques")
	private List<AttaqueJson> AttaqueListe;
}
