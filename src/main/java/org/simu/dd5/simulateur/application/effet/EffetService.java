package org.simu.dd5.simulateur.application.effet;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.degats.DegatsService;
import org.simu.dd5.simulateur.domaine.effet.EffetEchec;
import org.simu.dd5.simulateur.domaine.effet.EffetReussite;
import org.simu.dd5.simulateur.domaine.effet.HasEffetEchec;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EffetService {
	private static final Logger logger = LoggerFactory.getLogger(EffetService.class);

    private final DegatsService degatsService;

	public SousResultatAttaque quelEffetEvasion(ResultatTestDDEnum resultatTestDD, HasEffetEchec effet, Opposant defenseur) {
		logger.debug("L'attaque avec évasion est {}", resultatTestDD);

		if(resultatTestDD.estReussie()) {
			return quelEffetReussite(effet.getEffetReussite(), effet.getEffetEchec(), defenseur);
		} else {
			return quelEffetEchec(effet.getEffetEchec(), defenseur);
		}
	}

	public SousResultatAttaque quelEffetEchec(EffetEchec effetEchec, Opposant defenseur) {
		SousResultatAttaque sousResultatAttaque = SousResultatAttaque.ECHEC();

		if(effetEchec.getDegats() != null && !effetEchec.getDegats().isEmpty()) {
			sousResultatAttaque = degatsService.quelDegats(ResultatTestDDEnum.ECHEC, effetEchec.getDegats(), defenseur, false);
		}

		if(effetEchec.getEtatSet() != null && !effetEchec.getEtatSet().isEmpty()) {
			if(sousResultatAttaque == null) {
				throw new RuntimeException();
			}
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
