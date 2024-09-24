package org.simu.dd5.simulateur.domaine.aleatoire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.aleatoire.typeenum.TypeDesEnum;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class NombreDeDes {
	private int nombreDes;
	private TypeDesEnum typeDes;
	private int complement;

	public int getValeurRandom() {
		int accumulateur = complement;
		for(int i=0;i<nombreDes;i++){
			accumulateur += typeDes.valeurRandom();
		}

		return accumulateur;
	}
}