package org.simu.dd5.simulateur.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.application.attaque.AttaqueService;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.simu.dd5.simulateur.application.round.RoundService;
import org.simu.dd5.simulateur.application.situation.SituationService;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.ResultatPlusieursCombat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChargementController {
	private final ChargementService chargementService;
	private final RoundService roundService;

	List<Opposant> opposantListe;

	@GetMapping("/charge")
	public void chargement() {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		Opposant quasit = opposantListe.stream().filter(o -> o.getNom().equals("Quasit")).findFirst().orElse(null);
		Opposant arcanaloth = opposantListe.stream().filter(o -> o.getNom().equals("Arcanaloth")).findFirst().orElse(null);

		System.out.println(roundService.lancePlusieursCombats(quasit, arcanaloth, 10));
	}

	@GetMapping("/combat/{nom1}/{nom2}")
	public ResultatPlusieursCombat attaque(@PathVariable String nom1, @PathVariable String nom2) {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		Opposant opposantA = opposantListe.stream().filter(o -> o.getNom().equals(nom1)).findFirst().orElse(null);
		Opposant opposantB = opposantListe.stream().filter(o -> o.getNom().equals(nom2)).findFirst().orElse(null);

		return roundService.lancePlusieursCombats(opposantA, opposantB, 30);
	}
}
