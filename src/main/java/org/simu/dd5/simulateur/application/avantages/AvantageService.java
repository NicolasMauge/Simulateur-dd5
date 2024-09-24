package org.simu.dd5.simulateur.application.avantages;

import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.springframework.stereotype.Service;

import static org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum.*;
import static org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum.NEUTRE;

@Service
public class AvantageService {
	public AvantageEnum syntheseAvantagePourAttaquant(Opposant attaquant, Opposant defenseur) {
		return switch (attaquant.aQuelAvantage()) {
			case AVANTAGE -> AvantageEnum.AVANTAGE;
			case DESAVANTAGE -> {
				if (defenseur.donneUnAvantagePourAttaquant()) {
					// on considère que l'avantage donné par le défenseur contrebalance le désavantage de l'attaquant
					yield NEUTRE;
				}
				yield DESAVANTAGE;
			}
			case NEUTRE -> {
				if (defenseur.donneUnAvantagePourAttaquant()) {
					yield AVANTAGE;
				}
				yield NEUTRE;
			}
			case AUCUNE_ACTION -> AvantageEnum.AUCUNE_ACTION;
		};
	}
}
