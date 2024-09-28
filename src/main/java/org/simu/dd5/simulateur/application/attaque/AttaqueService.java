package org.simu.dd5.simulateur.application.attaque;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.aleatoire.D20Service;
import org.simu.dd5.simulateur.application.avantages.AvantageService;
import org.simu.dd5.simulateur.application.degats.DegatsService;
import org.simu.dd5.simulateur.application.effet.EffetService;
import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttaqueService {
	private static final Logger logger = LoggerFactory.getLogger(AttaqueService.class);

	private final EffetService effetService;
	private final AttaqueToucherService attaqueToucherService;
	private final AttaqueEvasionService attaqueEvasionService;

	public ResultatAttaque lanceAttaque(Attaque attaque, Opposant attaquant, Opposant defenseur) {
		// est-ce que c'est une attaque avec touche ?
		if (attaque.estNonCoherente()) {
			logger.warn("L'attaque choisie n'est pas cohérente");
			return null;
		}

		// neutralisé
		if(attaquant.estNeutralise()) {
			logger.debug("L'attaquant est neutralisé");
			return ResultatAttaque.EN_ECHEC(null);
		}

		// incapable d'agir
		if (attaquant.estIncapableDAgir()) {
			logger.debug("L'attaquant est incapable d'agir");
			return ResultatAttaque.EN_ECHEC(null);
		}

		return switch (attaque.estDeQuelType()) {
			case ATTAQUE_AVEC_TOUCHER -> attaqueToucherService.lanceAttaqueAvecToucher(attaque, attaquant, defenseur);
			case EVASION -> {
				ResultatTestDDEnum resultatTest = attaqueEvasionService.lancerUnTestEvasion(attaque.getTest(), defenseur);
				yield new ResultatAttaque(effetService.quelEffetEvasion(resultatTest, attaque.getEffet(), defenseur));
			}
			case TRAITS -> ResultatAttaque.EST_UN_TRAIT();
			case NON_DEFINIE -> ResultatAttaque.EN_ECHEC(null);
		};
	}
}
