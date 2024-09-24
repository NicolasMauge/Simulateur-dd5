package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.EffetReussite;
import org.simu.dd5.simulateur.domaine.degats.EffetReussiteJson;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EffetReussiteJsonToEffetReussiteMapper {
	private final DegatsJsonToDegatsMapper degatsJsonToDegatsMapper;

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
	public EffetReussite mapToEffetReussite(EffetReussiteJson effetReussiteJson) {
		if(effetReussiteJson == null) {
			return null;
		}

		return new EffetReussite(
				effetReussiteJson.getDegatsMinimum(),
				degatsJsonToDegatsMapper.mapToListeDegats(effetReussiteJson.getDegats()));
	}
}
