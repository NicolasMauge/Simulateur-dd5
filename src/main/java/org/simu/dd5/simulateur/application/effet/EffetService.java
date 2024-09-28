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

//	public SousResultatAttaque quelResultatSiEffet(Effet effet, Opposant defenseur) {
//		JetSauvegarde jetSauvegarde = effet.getTest().getJetSauvegarde();
//
//		// est-ce que la tentative d'éviter ces effets supplémentaires est réussie ?
//		ResultatTestDDEnum tentativePourEviter = evadeSuiteJetSauvegarde(
//				jetSauvegarde,
//				defenseur.getValeurCompetence(jetSauvegarde.getCompetence()),
//				defenseur.aUnAvantagePourTestSur(jetSauvegarde.getCompetence()));
//
//		if (tentativePourEviter.estReussie() && effet.getEffetReussite() == null) {
//			return SousResultatAttaque.REUSSIE_SANS_EFFET();
//		}
//
//		if (tentativePourEviter.estReussie()) {
//			return degatsPourEvasion(tentativePourEviter, effet.getEffetReussite(), effet.getEffetEchec(), defenseur);
//		}
//
//		if(effet.getEffetEchec().getDegats() != null) {
//			return degatsService.quelsSontLesDegatsRecus(tentativePourEviter, effet.getEffetEchec().getDegats(), defenseur, false);
//		}
//
//		if(effet.getEffetEchec().getEtatSet() != null && !effet.getEffetEchec().getEtatSet().isEmpty()) {
//			// TODO : gérer les cas où les nouveaux états ne sont pas une liste mais une map (s'il y a une durée...)
//			return SousResultatAttaque.REUSSIE_SANS_EFFET().ajoutDesEtats(effet.getEffetEchec().getEtatSet());
//		}
//
//		logger.warn("Pas de dégât et pas de modification d'état : {}", effet.getEffetEchec());
//		return null;
//	}

//	private SousResultatAttaque degatsPourEvasion(ResultatTestDDEnum resultatTest, EffetReussite effetReussite, EffetEchec effetEchec, Opposant defenseur) {
//		// Cas 1 : des dégâts sont spécifiés
//		if (effetReussite.getDegats() != null) {
//			return degatsService.quelsSontLesDegatsRecus(resultatTest, effetReussite.getDegats(), defenseur, false);
//		}
//
//		// Cas 2 : les dégâts sont spécifiés sous forme de dégâts minimum
//		if (!effetReussite.getDegatsMinimum().equals("MOITIE")) {
//			logger.warn("Les dégâts minimum doit être égal à MOITIE : {}", effetReussite);
//			return SousResultatAttaque.REUSSIE_SANS_EFFET();
//		}
//
//		// on récupère les dégâts dans effetEchec
//		if (effetEchec.getDegats() != null) {
//			return degatsService.quelsSontLesDegatsRecus(resultatTest, effetEchec.getDegats(), defenseur, false).moitie();
//		}
//
//		logger.warn("Les dégâts sont sensés être moitié des dégâts en échec, mais il n'y a pas de dégât en échec : {}", effetEchec);
//		return SousResultatAttaque.REUSSIE_SANS_EFFET();
//	}

	public SousResultatAttaque quelEffetEvasion(ResultatTestDDEnum resultatTestDD, Effet effet, Opposant defenseur) {
		if(resultatTestDD.estReussie()) {
			return quelEffetReussite(effet.getEffetReussite(), effet.getEffetEchec(), defenseur);
		} else {
			return quelEffetEchec(effet.getEffetEchec(), defenseur);
		}
	}

	public SousResultatAttaque quelEffetEchec(EffetEchec effetEchec, Opposant defenseur) {
		SousResultatAttaque sousResultatAttaque = SousResultatAttaque.ECHEC();

		if(effetEchec.getDegats() != null) {
			sousResultatAttaque = degatsService.quelDegats(ResultatTestDDEnum.ECHEC, effetEchec.getDegats(), defenseur, false);
		}

		if(effetEchec.getEtatSet() != null) {
			sousResultatAttaque.ajoutDesEtats(effetEchec.getEtatSet());
		}

		return sousResultatAttaque;
	}

	public SousResultatAttaque quelEffetReussite(EffetReussite effetReussite, EffetEchec effetEchec, Opposant defenseur) {
		if(effetReussite == null) {
			return SousResultatAttaque.REUSSIE_SANS_EFFET();
		}

		// si des dégâts sont définis
		if (effetReussite.getDegats() != null) {
			return degatsService.quelDegats(ResultatTestDDEnum.REUSSITE, effetReussite.getDegats(), defenseur, false);
		}

		// sinon, ce sont des dégâts minimum
		return degatsService.quelDegats(ResultatTestDDEnum.REUSSITE, effetEchec.getDegats(), defenseur, false).moitie();
	}
}
