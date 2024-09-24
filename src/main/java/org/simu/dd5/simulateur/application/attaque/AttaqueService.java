package org.simu.dd5.simulateur.application.attaque;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.aleatoire.D20Service;
import org.simu.dd5.simulateur.application.avantages.AvantageService;
import org.simu.dd5.simulateur.application.degats.DegatsService;
import org.simu.dd5.simulateur.application.effet.EffetService;
import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.degats.Effet;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttaqueService {
	private static final Logger logger = LoggerFactory.getLogger(AttaqueService.class);

	private final D20Service d20Service;
	private final DegatsService degatsService;
	private final AvantageService avantageService;
	private final EffetService effetService;

	public ResultatAttaque lanceAttaque(Attaque attaque, Opposant attaquant, Opposant defenseur) {
		// est-ce que c'est une attaque avec touche ?
		if (attaque.getTest() == null) {
			logger.warn("L'attaque choisie n'a pas de test d'attaque : {}", attaque);
			return null;
		}

		if (estAttaqueAvecToucher(attaque)) {
			return lanceAttaqueAvecToucher(attaque, attaquant, defenseur);
		}

		// TODO si autre type de test nécessaire

		return ResultatAttaque.EN_ECHEC(null);
	}

	private ResultatAttaque lanceAttaqueAvecToucher(Attaque attaque, Opposant attaquant, Opposant defenseur) {
		// neutralisé
		if(attaquant.estNeutralise()) {
			logger.info("L'attaquant est neutralisé");
			return ResultatAttaque.EN_ECHEC(null);
		}

		// incapable d'agir
		if (attaquant.estIncapableDAgir()) {
			logger.info("L'attaquant est incapable d'agir");
			return ResultatAttaque.EN_ECHEC(null);
		}

		// pour une attaque avec touché, il faut avoir un effet
		if (attaque.getEffet() == null) {
			logger.warn("Il n'y a pas d'effet pour l'attaque {}", attaque);
			return ResultatAttaque.EN_ECHEC(null);
		}

		AvantageEnum avantageAttaquant = avantageService.syntheseAvantagePourAttaquant(attaquant, defenseur);

		// est-ce que l'attaque touche ?
		ResultatTestDDEnum tentativePourToucher = toucheOuNon(attaque.getTest(), defenseur.getClasseArmure(), avantageAttaquant);

		if (tentativePourToucher.estReussie()) {
			return quelResultatSiAttaqueATouche(tentativePourToucher, attaque.getEffet(), defenseur);
		}

		// pour ce type d'attaque, il n'y a pas d'effet en cas d'échec du test
		return ResultatAttaque.EN_ECHEC(tentativePourToucher);
	}

	private ResultatAttaque quelResultatSiAttaqueATouche(ResultatTestDDEnum resultatTest, Effet effet, Opposant defenseur) {
		if (effet.getDegats() == null || effet.getDegats().isEmpty()) {
			logger.warn("Il n'y a pas de dégâts associées à l'attaque {}", effet);
			return ResultatAttaque.EN_ECHEC(resultatTest);
		}

		// les dégâts pour l'attaque avec 'touché'
		ResultatAttaque resultatAttaque = new ResultatAttaque(
				degatsService.quelsSontLesDegatsRecus(
						resultatTest,
						effet.getDegats(),
						defenseur,
						resultatTest==ResultatTestDDEnum.REUSSITE_CRITIQUE)
		);

		//
		if (effet.getTest() == null) {
			return resultatAttaque;
		}

		SousResultatAttaque resultatAttaqueEffet = effetService.quelResultatSiEffet(effet, defenseur);

		return resultatAttaque.ajouteResultatAttaqueEvasion(resultatAttaqueEffet);
	}

	private ResultatTestDDEnum toucheOuNon(DDTestReussite test, int classeArmure, AvantageEnum avantage) {
		return d20Service.testDegreDifficulte(test.getBonusToucher(), classeArmure, avantage);
	}

	private boolean estAttaqueAvecToucher(Attaque attaque) {
		return attaque.getTest() != null && attaque.getTest().getBonusToucher() != null;
	}
}
