package org.simu.dd5.simulateur.domaine.resultat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class ResultatCombat {
	enum GagnantEnum {
		OPPOSANT_A, OPPOSANT_B
	}

	private UUID uuidOpposantA;
	private String nomOpposantA;
	private UUID uuidOpposantB;
	private String nomOpposantB;

	private GagnantEnum gagnant;

	public UUID quiAGagne() {
		if (gagnant == null) {
			return null;
		}

		return gagnant == GagnantEnum.OPPOSANT_A ? uuidOpposantA : uuidOpposantB;
	}

	public void setGagnant(UUID uuidGagnant) {
		if (uuidGagnant != null) {
			if(uuidGagnant == uuidOpposantA) {
				gagnant = GagnantEnum.OPPOSANT_A;
				return;
			}
			if(uuidGagnant == uuidOpposantB) {
				gagnant = GagnantEnum.OPPOSANT_B;
			}
		}
	}
}
