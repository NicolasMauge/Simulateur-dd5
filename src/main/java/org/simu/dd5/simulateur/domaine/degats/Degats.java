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

	public Integer getValeurMoyenne() {
		if(valeurDegatsMoyens != 0) {
			return valeurDegatsMoyens;
		}

		if(nombreDeDesDegats == null) {
			return null;
		}

		double valeurMoyenneDe = switch(nombreDeDesDegats.getTypeDes()) {
			case D4 -> 2.5;
			case D6 -> 3.5;
			case D8 -> 4.5;
			case D10 -> 5.5;
			case D12 -> 6.5;
			case D20 -> 10.5;
		};

		return (int) (valeurMoyenneDe * nombreDeDesDegats.getNombreDes() + nombreDeDesDegats.getComplement());
	}
}
