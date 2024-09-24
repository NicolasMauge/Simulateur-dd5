package org.simu.dd5.simulateur.domaine.touche;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DDTestReussiteJson {
	@JsonProperty("type")
	private String typeAttaque;

	@JsonProperty("type-arme")
	private String typeArme;

	@JsonProperty("type-distance")
	private String typeDistance;

	@JsonProperty("bonus-toucher")
	private String bonusToucher;

	@JsonProperty("jet-sauvegarde")
	private JetSauvegardeJson jetSauvegarde;
}
