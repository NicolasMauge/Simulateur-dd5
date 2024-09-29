package org.simu.dd5.simulateur.application.round;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.attaque.AttaqueService;
import org.simu.dd5.simulateur.application.attaque.ChoixAttaque;
import org.simu.dd5.simulateur.application.elo.ClassementEloService;
import org.simu.dd5.simulateur.application.situation.SituationService;
import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.ResultatCombat;
import org.simu.dd5.simulateur.domaine.resultat.ResultatPlusieursCombat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoundService {
	private static final Logger logger = LoggerFactory.getLogger(RoundService.class);

	private final AttaqueService attaqueService;
	private final SituationService situationService;
	private final ChoixAttaque choixAttaque;
	private final ClassementEloService classementEloService;

	public ResultatPlusieursCombat lancePlusieursCombats(Opposant opposantA, Opposant opposantB, int nombreCombats) throws Exception {
		if (opposantA == null || opposantB == null) {
			logger.debug("Un des opposants est null : {}, {}", opposantA, opposantB);
			return null;
		}

//		if(choixAttaque.choisiPremiereAttaqueAvecToucher(opposantA) == null) {
//			logger.debug("Il n'y a pas d'attaque avec toucher pour {}", opposantA);
//			return null;
//		}

//		if(choixAttaque.choisiPremiereAttaqueAvecToucher(opposantB) == null) {
//			logger.debug("Il n'y a pas d'attaque avec toucher pour {}", opposantB);
//			return null;
//		}

		ResultatPlusieursCombat resultatPlusieursCombat = new ResultatPlusieursCombat(
				opposantA.getUuid(), opposantA.getNom(),
				opposantB.getUuid(), opposantB.getNom(),
				null
		);

		for (int i = 0; i < nombreCombats; i++) {
			opposantA.reinitialiseSituation();
			opposantB.reinitialiseSituation();

			UUID uuidGagnant = faitUnCombat(opposantA, opposantB);

			resultatPlusieursCombat.ajouteUnGagnant(uuidGagnant);
		}

		classementEloService.calculNouveauELO(opposantA, opposantB, resultatPlusieursCombat.gagnantGlobal());

		return resultatPlusieursCombat;
	}

	public ResultatCombat lanceLeCombat(Opposant opposantA, Opposant opposantB) throws Exception {
		if (opposantA == null || opposantB == null) {
			logger.debug("Un des opposants est null : {}, {}", opposantA, opposantB);
			return null;
		}

		ResultatCombat resultatCombat = new ResultatCombat(
				opposantA.getUuid(), opposantA.getNom(),
				opposantB.getUuid(), opposantB.getNom(),
				null
		);

		UUID uuidGagnant = faitUnCombat(opposantA, opposantB);

		resultatCombat.setGagnant(uuidGagnant);

		return resultatCombat;
	}

	private UUID faitUnCombat(Opposant opposantA, Opposant opposantB) throws Exception {
		UUID uuidGagnant = null;
		while (uuidGagnant == null) {
			if(opposantA.estIncapableDAgir() && opposantB.estIncapableDAgir()) {
				logger.debug("Match null : {} est {} et {} est {}",
						opposantA.getNom(), opposantA.getSituationOpposant().getEtatsMap(),
						opposantB.getNom(), opposantB.getSituationOpposant().getEtatsMap());
				break;
			}

			if(opposantA.getSituationOpposant().getAttaqueEnEchecSuccessives() >= 5 && opposantB.getSituationOpposant().getAttaqueEnEchecSuccessives() >= 5) {
				logger.debug("Match null : {} et {} n'arrivent pas à faire aboutir leurs attaques",
						opposantA.getNom(),
						opposantB.getNom());
				break;
			}

			uuidGagnant = lanceUnRound(opposantA, opposantB);
		}

		return uuidGagnant;
	}

	public UUID lanceUnRound(Opposant opposantA, Opposant opposantB) throws Exception {
		demiRound(opposantA, opposantB);

		if (opposantB.estNeutralise()) {
			logger.debug("{} a gagné", opposantA.getNom());
			return opposantA.getUuid();
		}

		demiRound(opposantB, opposantA);

		if (opposantA.estNeutralise()) {
			logger.debug("{} a gagné", opposantB.getNom());
			return opposantB.getUuid();
		}

		return null;
	}

	private void demiRound(Opposant attaquant, Opposant defenseur) {
		//Attaque attaque = choixAttaque.choisiPremiereAttaqueAvecToucher(attaquant);
		Attaque attaque = choixAttaque.choisiAttaqueAleatoire(attaquant);

		if(!attaquant.estNeutralise() && !attaquant.estIncapableDAgir()) {
			ResultatAttaque resultatAttaque = attaqueService.lanceAttaque(attaque, attaquant, defenseur);

			logger.debug(">> {}", defenseur.getSituationOpposant());
			//System.out.println(defenseur.getSituationOpposant());
			logger.debug("Resultat de l'attaque de {} sur {} : {}", attaquant.getNom(), defenseur.getNom(), resultatAttaque);

			if (resultatAttaque == null || resultatAttaque.getTotalDegats() == 0) {
				attaquant.getSituationOpposant().ajouteUneAttaqueRatee();
			} else {
				attaquant.getSituationOpposant().uneAttaqueAPorte();
			}

			if (resultatAttaque == null) {
				return;
			}

			situationService.miseAJourSituationOpposant(defenseur, resultatAttaque);
		} else {
			attaquant.getSituationOpposant().ajouteUneAttaqueRatee();
		}

		logger.debug("{} : >> {}", defenseur.getNom(), defenseur.getSituationOpposant());
	}
}
