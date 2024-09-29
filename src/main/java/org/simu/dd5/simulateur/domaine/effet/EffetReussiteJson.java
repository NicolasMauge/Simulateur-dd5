package org.simu.dd5.simulateur.domaine.effet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simu.dd5.simulateur.domaine.degats.DegatsJson;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EffetReussiteJson {
	// Exemple : "effet-reussite": {
	//                            "degats": [
	//                                {
	//                                    "valeur-degats": "5 (1d4 + 3)",
	//                                    "type-degats": "tranchants"
	//                                }
	//                            ],
	//                            "condition": false
	//                        }
	// Ou bien juste :
	// "effet-reussite": {
	//                    "degats-minimum": "MOITIE"
	//                }
	@JsonProperty("degats-minimum")
	private String degatsMinimum;
	private List<DegatsJson> degats;
}
