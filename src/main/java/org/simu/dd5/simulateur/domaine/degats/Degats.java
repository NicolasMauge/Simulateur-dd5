package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.aleatoire.NombreDeDes;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;

@Getter
@AllArgsConstructor
@ToString
public class Degats {
	private int valeurDegatsMoyens;
	private NombreDeDes nombreDeDesDegats;
	private TypeDegatEnum typeDegats;

	public Integer getValeurRandom() {
		if(nombreDeDesDegats == null) {
			return null;
		}

		return nombreDeDesDegats.getValeurRandom();
	}
}
