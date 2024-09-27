package org.simu.dd5.simulateur.exposition;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.simu.dd5.simulateur.application.round.RoundService;
import org.simu.dd5.simulateur.domaine.opposant.Antagonistes;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatPlusieursCombat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChargementController {
	private static final Logger logger = LoggerFactory.getLogger(ChargementController.class);

	private final ChargementService chargementService;
	private final RoundService roundService;

	private List<Opposant> opposantListe;
	private List<Antagonistes> antagonistesListe;


	@GetMapping("/charge")
	public void chargement() {
		opposantListe = chargementService.chargeOpposants();

		Opposant opposantA = opposantListe.stream().filter(o -> o.getNom().equals("Quasit")).findFirst().orElse(null);

		System.out.println(opposantA);
	}

	@GetMapping("/combat/{nom1}/{nom2}")
	public ResultatPlusieursCombat attaqueDeDeuxOpposants(@PathVariable String nom1, @PathVariable String nom2) throws Exception {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		Opposant opposantA = opposantListe.stream().filter(o -> o.getNom().equals(nom1)).findFirst().orElse(null);
		Opposant opposantB = opposantListe.stream().filter(o -> o.getNom().equals(nom2)).findFirst().orElse(null);

		return roundService.lancePlusieursCombats(opposantA, opposantB, 30);
	}

	@GetMapping("/combat/aleatoires")
	public void attaquesAleatoires() throws Exception {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		if (antagonistesListe == null || antagonistesListe.isEmpty()) {
			antagonistesListe = new ArrayList<>();
			for (int i = 0; i < opposantListe.size(); i++) {
				for (int j = 0; j < i; j++) {
					antagonistesListe.add(new Antagonistes(opposantListe.get(i), opposantListe.get(j)));
				}
			}
		}

		//assert antagonistesListe != null;
		Collections.shuffle(antagonistesListe);

		int compteur = 0;
		//for(int i=0;i<5;i++) { // nombre de saisons de combats
		for (Antagonistes antagonistes : antagonistesListe) {
			roundService.lancePlusieursCombats(antagonistes.getOpposantA(), antagonistes.getOpposantB(), 1);
			compteur += 1;
//				if(compteur == 100) {
//					return;
//				}
		}
		//}

		opposantListe.forEach(o -> System.out.println(o.getNom() + " : " + o.getClassementELO()));
		System.out.println();
	}
}
