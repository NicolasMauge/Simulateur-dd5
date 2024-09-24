package org.simu.dd5.simulateur.domaine.degats;

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
public class DegatsJson {
	@JsonProperty("valeur-degats")
	private String valeurDegats;

	@JsonProperty("type-degats")
	private String typeDegats;
}
