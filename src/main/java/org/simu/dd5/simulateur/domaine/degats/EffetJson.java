package org.simu.dd5.simulateur.domaine.degats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussiteJson;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EffetJson {
	private List<DegatsJson> degats;
	private DDTestReussiteJson test;
	@JsonProperty("effet-echec")
	private EffetEchecJson effetEchec;
	@JsonProperty("effet-reussite")
	private EffetReussiteJson effetReussite;
}