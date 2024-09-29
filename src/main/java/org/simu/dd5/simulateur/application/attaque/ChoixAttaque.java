package org.simu.dd5.simulateur.application.attaque;

import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.springframework.stereotype.Service;

@Service
public class ChoixAttaque {
	public Attaque choisiUneAttaque(Opposant opposant) {
		return opposant.getListeAttaques()
				.stream()
				.filter(Attaque::estCoherente)
				.filter(Attaque::estUneAttaqueAvecToucher)
				.findFirst()
				.orElse(null);
	}
}
