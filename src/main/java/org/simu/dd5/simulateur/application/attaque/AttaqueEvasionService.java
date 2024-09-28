package org.simu.dd5.simulateur.application.attaque;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.aleatoire.D20Service;
import org.simu.dd5.simulateur.application.effet.EffetService;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.simu.dd5.simulateur.domaine.touche.JetSauvegarde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttaqueEvasionService {
	private static final Logger logger = LoggerFactory.getLogger(AttaqueEvasionService.class);

	private final D20Service d20Service;
	private final EffetService effetService;

	public ResultatTestDDEnum lancerUnTestEvasion(DDTestReussite test, Opposant defenseur) {
		// est-ce que la tentative d'éviter ces effets supplémentaires est réussie ?
		return evadeSuiteJetSauvegarde(
				test.getJetSauvegarde(),
				defenseur.getValeurCompetence(test.getJetSauvegarde().getCompetence()),
				defenseur.aUnAvantagePourTestSur(test.getJetSauvegarde().getCompetence()));
	}

	private ResultatTestDDEnum evadeSuiteJetSauvegarde(JetSauvegarde jetSauvegarde, int bonus, AvantageEnum avantage) {
		return d20Service.testDegreDifficulte(bonus, jetSauvegarde.getDd(), avantage);
	}
}
