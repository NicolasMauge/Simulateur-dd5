package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.Effet;
import org.simu.dd5.simulateur.domaine.degats.EffetJson;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EffetJsonToEffetMapper {
	private final DegatsJsonToDegatsMapper degatsJsonToDegatsMapper;
	private final EffetEchecJsonToEffetEchecMapper effetEchecJsonToEffetEchecMapper;
	private final DDTestReussiteJsonToDDTestReussiteMapper ddTestReussiteJsonToDDTestReussiteMapper;
	private final EffetReussiteJsonToEffetReussiteMapper effetReussiteJsonToEffetReussiteMapper;

	// Exemple : "effet": {
	//                    "degats": [
	//                        {
	//                            "valeur-degats": "8 (2d4 + 3)",
	//                            "type-degats": "tranchants"
	//                        }
	//                    ],
	//                    "test": {
	//                        "jet-sauvegarde": {
	//                            "competence": "Constitution",
	//                            "dd": "14"
	//                        }
	//                    },
	//                    "effet-echec": {
	//                        "degats": [
	//                            {
	//                                "valeur-degats": "10 (3d6)",
	//                                "type-degats": "poison"
	//                            }
	//                        ]
	//                    },
	//                    "effet-reussite": {
	//                        "degats-minimum": "MOITIE"
	//                    }
	//                }
	public Effet mapToEffet(EffetJson effetJson) {
		if (effetJson == null) {
			return null;
		}

		return new Effet(
				degatsJsonToDegatsMapper.mapToListeDegats(effetJson.getDegats()),
				ddTestReussiteJsonToDDTestReussiteMapper.mapToDDTestReussite(effetJson.getTest()),
				effetEchecJsonToEffetEchecMapper.mapToEffetEchec(effetJson.getEffetEchec()),
				effetReussiteJsonToEffetReussiteMapper.mapToEffetReussite(effetJson.getEffetReussite())
		);
	}
}
