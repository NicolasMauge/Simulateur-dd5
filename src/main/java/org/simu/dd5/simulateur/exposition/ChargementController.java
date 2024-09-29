package org.simu.dd5.simulateur.exposition;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.attaque.ChoixAttaque;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.simu.dd5.simulateur.application.commun.Affichage;
import org.simu.dd5.simulateur.application.commun.ToStringPretty;
import org.simu.dd5.simulateur.application.round.RoundService;
import org.simu.dd5.simulateur.domaine.opposant.Antagonistes;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatPlusieursCombat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChargementController {
	private static final Logger logger = LoggerFactory.getLogger(ChargementController.class);

	private final ChargementService chargementService;
	private final RoundService roundService;
	private final ToStringPretty toStringPretty;
	private final ChoixAttaque choixAttaque;
	private final Affichage affichage;

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

		// TODO : pour l'instant, on n'utilise que des attaques avec toucher
		opposantListe = opposantListe
				.stream()
				.filter(Opposant::aAuMoinsUneAttaque)
				.toList();

//		opposantListe.forEach(o -> {
//			Attaque attaque = choixAttaque.choisiPremiereAttaqueAvecToucher(o);
//			if(attaque == null) {
//				logger.error("Nom : {}, attaque nulle", o.getNom());
//			}
//		});

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
		}
		//}

		// affichage.afficheOpposantsClasses(opposantListe);
		toStringPretty.save_complet(opposantListe);

		// analyse
		Collections.shuffle(antagonistesListe);

		//for(int i=0;i<5;i++) { // nombre de saisons de combats
		for (Antagonistes antagonistes : antagonistesListe) {
			Opposant opposantA = antagonistes.getOpposantA();
			Opposant opposantB = antagonistes.getOpposantB();

			//if(opposantB)
		}
	}

	@GetMapping("/creature/{nomCreature}")
	public String getCreature(@PathVariable String nomCreature) {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		Opposant opposant = opposantListe.stream().filter(o-> o.getNom().equalsIgnoreCase(nomCreature)).findFirst().orElse(null);

		return toStringPretty.cleanJsonFromObject(opposant);
	}

	@GetMapping("/creatures/save")
	public void saveCreatures() {
		if (opposantListe == null || opposantListe.isEmpty()) {
			opposantListe = chargementService.chargeOpposants();
		}

		toStringPretty.save_complet(opposantListe);
	}
}
