package org.simu.dd5.simulateur.application.situation;

import org.simu.dd5.simulateur.domaine.etats.SituationOpposant;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.springframework.stereotype.Service;

@Service
public class SituationService {
	public void miseAJourSituationOpposant(Opposant opposant, ResultatAttaque resultatAttaque) {
		if(resultatAttaque == null) {
			return;
		}

		if(resultatAttaque.getDegatsEtConditionAttaqueToucher() != null) {
			miseAJourPour(opposant.getSituationOpposant(), resultatAttaque.getDegatsEtConditionAttaqueToucher());
		}

		if(resultatAttaque.getDegatsEtConditionEvasion() != null) {
			miseAJourPour(opposant.getSituationOpposant(), resultatAttaque.getDegatsEtConditionEvasion());
		}
	}

	private void miseAJourPour(SituationOpposant situationOpposant, SousResultatAttaque sousResultatAttaqueAvecToucher) {
		situationOpposant.reduitLaVieDe(sousResultatAttaqueAvecToucher.getTotalDegats());
		situationOpposant.ajouteDesEtats(sousResultatAttaqueAvecToucher.getEtatsSupplementairesMap());
	}
}
