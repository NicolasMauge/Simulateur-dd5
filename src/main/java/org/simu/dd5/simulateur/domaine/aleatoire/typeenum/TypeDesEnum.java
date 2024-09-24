package org.simu.dd5.simulateur.domaine.aleatoire.typeenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@Getter
@AllArgsConstructor
public enum TypeDesEnum {
	D4("d4", 4),
	D6("d6", 6),
	D8("d8", 8),
	D10("d10", 10),
	D12("d12", 12),
	D20("d20", 20);

	private final String typeDe;
	private final int numSides;

	public int valeurRandom() {
		Random r = new Random();
		return r.nextInt(this.numSides)+1;
	}

	public static TypeDesEnum fromValeur(String s) {
		for (TypeDesEnum e : TypeDesEnum.values()) {
			if (e.getTypeDe().equals(s)) {
				return e;
			}
		}

		throw new IllegalArgumentException("Aucune constante avec la valeur " + s + " n'a été trouvée.");
	}
}
