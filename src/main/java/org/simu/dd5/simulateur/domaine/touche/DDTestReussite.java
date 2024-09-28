package org.simu.dd5.simulateur.domaine.touche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.attaque.typeenum.TypeAttaque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@AllArgsConstructor
@ToString
public class DDTestReussite {
	private static final Logger logger = LoggerFactory.getLogger(DDTestReussite.class);

	private String typeAttaque;
	private String typeArme;
	private String typeDistance;
	private Integer bonusToucher;
	private JetSauvegarde jetSauvegarde;

	public boolean testMalDefini() {
		if((jetSauvegarde == null || jetSauvegarde.jetSauvegardeMalDefini()) && bonusToucher == null) {
			logger.debug("Un test doit avoir soit un jet de sauvegarde, soit un bonus de toucher");
			return true;
		}

		return false;
	}

	public TypeAttaque estDeQuelType() {
		if(testMalDefini()) {
			return TypeAttaque.NON_DEFINIE;
		}

		if(bonusToucher != null) {
			return TypeAttaque.ATTAQUE_AVEC_TOUCHER;
		}

		return TypeAttaque.EVASION;
	}

	public boolean estDeType(TypeAttaque typeAttaque) {
		return typeAttaque.equals(estDeQuelType());
	}
}
