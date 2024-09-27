package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.aleatoire.NombreDeDes;

@Getter
@AllArgsConstructor
@ToString
public class Degats {
	private int valeurDegatsMoyens;
	private NombreDeDes nombreDeDesDegats;

	public Integer getValeurRandom() {
		if(nombreDeDesDegats == null && valeurDegatsMoyens == 0) {
			return null;
		}

		return nombreDeDesDegats != null ? nombreDeDesDegats.getValeurRandom() : valeurDegatsMoyens;
	}
}
