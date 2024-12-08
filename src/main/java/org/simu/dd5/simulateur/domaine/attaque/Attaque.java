package org.simu.dd5.simulateur.domaine.attaque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.attaque.typeenum.TypeAttaque;
import org.simu.dd5.simulateur.domaine.effet.Effet;
import org.simu.dd5.simulateur.domaine.effet.EffetEchec;
import org.simu.dd5.simulateur.domaine.effet.EffetReussite;
import org.simu.dd5.simulateur.domaine.effet.HasEffetEchec;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@AllArgsConstructor
@ToString
public class Attaque implements HasEffetEchec {
	private static final Logger logger = LoggerFactory.getLogger(Attaque.class);

	private String typeAction;
	private String nomAttaque;
	private DDTestReussite test;
	private Effet effet;
	private EffetEchec effetEchec;
	private EffetReussite effetReussite;

	public boolean estNonCoherente() {
		// TODO : à voir si cette règle est cohérente avec les traits
		if(test == null || test.testMalDefini() || effetMalDefini()) {
			return true;
		}

		return effetEchec != null && effetEchec.effetEchecMalDefini();
	}

	public boolean estCoherente() {
		return !estNonCoherente();
	}

	private boolean effetMalDefini() {
		if(test == null) {
			logger.debug("Il n'y a pas de test");
			return true;
		}

		return switch (test.estDeQuelType()) {
			case ATTAQUE_AVEC_TOUCHER -> {
				if(effet == null || effet.effetMalDefini()) {
					logger.debug("Pour une attaque avec toucher, effet doit être bien rempli");
					yield true;
				}

				yield false;
			}
			case EVASION -> {
				if(effetEchec == null || effetEchec.effetEchecMalDefini()) {
					logger.debug("Pour une attaque de type évasion, effet échec doit être bien rempli");
					yield true;
				}

				// ce n'est pas grave si Effet Réussite est mal rempli
				yield false;
			}
			case TRAITS -> false;
			case NON_DEFINIE -> true;
		};
	}

	public TypeAttaque estDeQuelType() {
		if(test != null) {
			return test.estDeQuelType();
		}

		return TypeAttaque.NON_DEFINIE;
	}

	public boolean estUneAttaqueAvecToucher() {
		return estDeQuelType()==TypeAttaque.ATTAQUE_AVEC_TOUCHER;
	}

	public boolean estUneAttaque() {
		return switch (estDeQuelType()) {
            case ATTAQUE_AVEC_TOUCHER, EVASION -> true;
			case TRAITS, NON_DEFINIE -> false;
		};
	}
}
