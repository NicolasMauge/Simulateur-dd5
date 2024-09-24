package org.simu.dd5.simulateur.application.effet;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.aleatoire.D20Service;
import org.simu.dd5.simulateur.application.degats.DegatsService;
import org.simu.dd5.simulateur.domaine.degats.Effet;
import org.simu.dd5.simulateur.domaine.degats.EffetEchec;
import org.simu.dd5.simulateur.domaine.degats.EffetReussite;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.simu.dd5.simulateur.domaine.touche.JetSauvegarde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EffetService {
	private static final Logger logger = LoggerFactory.getLogger(EffetService.class);

	private final D20Service d20Service;
	private final DegatsService degatsService;

	public SousResultatAttaque quelResultatSiEffet(Effet effet, Opposant defenseur) {
		JetSauvegarde jetSauvegarde = effet.getTest().getJetSauvegarde();

		if (jetSauvegarde == null) {
			logger.warn("Ce n'est pas normal d'avoir un test sans jet de sauvegarde : {}", effet);
			return null;
		}

		if (effet.getEffetEchec() == null) {
			logger.warn("Ce n'est pas normal d'avoir un test sans effet échec : {}", effet);
			return null;
		}

		// TODO : il faudra voir comment gérer les avantages des défenseurs lors d'un jet de sauvegarde
		// est-ce que la tentative d'éviter ces effets supplémentaires est réussie ?
		ResultatTestDDEnum tentativePourEviter = evadeSuiteJetSauvegarde(
				jetSauvegarde,
				defenseur.getValeurCompetence(jetSauvegarde.getCompetence()),
				defenseur.aUnAvantagePourTestSur(jetSauvegarde.getCompetence()));

		if (tentativePourEviter.estReussie() && effet.getEffetReussite() == null) {
			return SousResultatAttaque.REUSSIE_SANS_EFFET();
		}

		if (tentativePourEviter.estReussie()) {
			return degatsPourEvasion(tentativePourEviter, effet.getEffetReussite(), effet.getEffetEchec(), defenseur);
		}

		return degatsService.quelsSontLesDegatsRecus(tentativePourEviter, effet.getEffetEchec().getDegats(), defenseur, false);
	}

	private SousResultatAttaque degatsPourEvasion(ResultatTestDDEnum resultatTest, EffetReussite effetReussite, EffetEchec effetEchec, Opposant defenseur) {
		if (effetReussite.getDegats() == null && effetReussite.getDegatsMinimum() == null) {
			logger.warn("Il n'y a pas de dégats ou de dégâts minimum dans effet réussite : {}", effetReussite);
			return SousResultatAttaque.REUSSIE_SANS_EFFET();
		}

		// Cas 1 : des dégâts sont spécifiés
		if (effetReussite.getDegats() != null) {
			return degatsService.quelsSontLesDegatsRecus(resultatTest, effetReussite.getDegats(), defenseur, false);
		}

		// Cas 2 : les dégâts sont spécifiés sous forme de dégâts minimum
		if (!effetReussite.getDegatsMinimum().equals("MOITIE")) {
			logger.warn("Les dégâts minimum doit être égal à MOITIE : {}", effetReussite);
			return SousResultatAttaque.REUSSIE_SANS_EFFET();
		}

		// on récupère les dégâts dans effetEchec
		if (effetEchec.getDegats() != null) {
			return degatsService.quelsSontLesDegatsRecus(resultatTest, effetEchec.getDegats(), defenseur, false).moitie();
		}

		logger.warn("Les dégâts sont sensés être moitié des dégâts en échec, mais il n'y a pas de dégât en échec : {}", effetEchec);
		return SousResultatAttaque.REUSSIE_SANS_EFFET();
	}

	private ResultatTestDDEnum evadeSuiteJetSauvegarde(JetSauvegarde jetSauvegarde, int bonus, AvantageEnum avantage) {
		return d20Service.testDegreDifficulte(bonus, jetSauvegarde.getDd(), avantage);
	}
}
