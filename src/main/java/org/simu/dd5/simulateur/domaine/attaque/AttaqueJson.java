package org.simu.dd5.simulateur.domaine.attaque;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.EffetJson;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussiteJson;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttaqueJson {
	@JsonProperty("type-action")
	private String typeAction;
	@JsonProperty("nom-attaque")
	private String nomAttaque;
	private DDTestReussiteJson test;
	private EffetJson effet;
}
