package org.simu.dd5.simulateur.exposition;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.attaque.AttaqueService;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.simu.dd5.simulateur.application.situation.SituationService;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChargementController {
	private final ChargementService chargementService;
	private final AttaqueService attaqueService;
	private final SituationService situationService;

	@GetMapping("/charge")
	public void chargement() {
		List<Opposant> opposantListe = chargementService.chargeOpposants();

		Opposant quasit = opposantListe.stream().filter(o -> o.getNom().equals("Quasit")).findFirst().orElse(null);
		Opposant arcanaloth = opposantListe.stream().filter(o -> o.getNom().equals("Arcanaloth")).findFirst().orElse(null);

		System.out.println(quasit);

		System.out.println(arcanaloth);


		if(quasit != null && arcanaloth != null) {
			ResultatAttaque resultatAttaqueAVersB = attaqueService.lanceAttaque(quasit.getListeAttaques().getFirst(), quasit, arcanaloth);


			System.out.println(arcanaloth.getSituationOpposant());

			System.out.println(resultatAttaqueAVersB);

			situationService.miseAJourSituationOpposant(arcanaloth, resultatAttaqueAVersB);

			System.out.println(arcanaloth.getSituationOpposant());


			ResultatAttaque resultatAttaqueBVersA = attaqueService.lanceAttaque(arcanaloth.getListeAttaques().getFirst(), arcanaloth, quasit);


			System.out.println(quasit.getSituationOpposant());

			System.out.println(resultatAttaqueBVersA);

			situationService.miseAJourSituationOpposant(quasit, resultatAttaqueBVersA);

			System.out.println(quasit.getSituationOpposant());


		}
	}
}
