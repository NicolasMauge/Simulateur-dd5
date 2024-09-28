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
public class AttaqueToucherService {
	private static final Logger logger = LoggerFactory.getLogger(AttaqueToucherService.class);

	private final D20Service d20Service;
	private final AvantageService avantageService;
	private final DegatsService degatsService;
	private final AttaqueEvasionService attaqueEvasionService;
	private final EffetService effetService;

	public ResultatAttaque lanceAttaqueAvecToucher(Attaque attaque, Opposant attaquant, Opposant defenseur) {
		AvantageEnum avantageAttaquant = avantageService.syntheseAvantagePourAttaquant(attaquant, defenseur);

		// est-ce que l'attaque touche ?
		ResultatTestDDEnum tentativePourToucher = toucheOuNon(attaque.getTest(), attaquant.getBonusDeMaitrise(), defenseur.getClasseArmure(), avantageAttaquant);

		if(!tentativePourToucher.estReussie()) {
			// pour ce type d'attaque, il n'y a pas d'effet en cas d'échec du test
			return ResultatAttaque.EN_ECHEC(tentativePourToucher);
		}

		SousResultatAttaque sousResultatAttaqueToucher = quelResultatSiAttaqueATouche(tentativePourToucher, attaque.getEffet(), defenseur);

		// s'il n'y a pas d'effet supplémentaire (à part les dégâts)
		DDTestReussite testEvasion = attaque.getEffet().getTest();
		if (testEvasion == null) {
			return new ResultatAttaque(sousResultatAttaqueToucher);
		}

		// s'il y a un test dans effet et donc potentiellement des effets supplémentaires
		ResultatTestDDEnum resultatTestEvasion =  attaqueEvasionService.lancerUnTestEvasion(testEvasion, defenseur);
		SousResultatAttaque sousResultatAttaqueEvasion = effetService.quelEffetEvasion(resultatTestEvasion, attaque.getEffet(), defenseur);

		return new ResultatAttaque(sousResultatAttaqueToucher, sousResultatAttaqueEvasion);
	}

	private SousResultatAttaque quelResultatSiAttaqueATouche(ResultatTestDDEnum resultatTest, Effet effet, Opposant defenseur) {
		// les dégâts pour l'attaque avec 'touché'
		return degatsService.quelDegats(
						resultatTest,
						effet.getDegats(),
						defenseur,
						resultatTest==ResultatTestDDEnum.REUSSITE_CRITIQUE);
	}

	private ResultatTestDDEnum toucheOuNon(DDTestReussite test, int bonusMaitrise, int classeArmure, AvantageEnum avantage) {
		return d20Service.testDegreDifficulte(test.getBonusToucher() + bonusMaitrise, classeArmure, avantage);
	}
}
