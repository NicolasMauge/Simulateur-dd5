package org.simu.dd5.simulateur.application.attaque;

import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ChoixAttaque {
	public Attaque choisiPremiereAttaqueAvecToucher(Opposant opposant) {
		return opposant.getListeAttaques()
				.stream()
				.filter(Attaque::estCoherente)
				.filter(Attaque::estUneAttaqueAvecToucher)
				.findFirst()
				.orElse(null);
	}

	public Attaque choisiAttaqueAleatoire(Opposant opposant) {
		List<Attaque> attaques = opposant.getListeAttaques()
				.stream()
				.filter(Attaque::estCoherente)
				.filter(Attaque::estUneAttaque)
				.toList();

		if(attaques.isEmpty()) {
			return null;
		}

		Random rand = new Random();
		int randomIndex = rand.nextInt(attaques.size());

		// Récupérer l'objet aléatoire
		return attaques.get(randomIndex);
	}
}
