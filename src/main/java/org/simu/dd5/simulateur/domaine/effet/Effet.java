package org.simu.dd5.simulateur.domaine.effet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Getter
@AllArgsConstructor
@ToString
public class Effet implements HasEffetEchec {
	private static final Logger logger = LoggerFactory.getLogger(Effet.class);

	private Map<TypeDegatEnum, Degats> degats;
	private DDTestReussite test;
	private EffetEchec effetEchec;
	private EffetReussite effetReussite;
	// TODO : est-ce qu'il peut y avoir des états ? modifier effetMalDefini dans ce cas

	public boolean effetMalDefini() {
		if(degats == null || degats.isEmpty()) {
			logger.debug("Les dégâts doivent être définis dans Effet");
			return true;
		}

		if(test != null) {
			if (test.testMalDefini()) {
				return true;
			}

			if (effetEchec == null || effetEchec.effetEchecMalDefini()) {
				logger.debug("Effet échec est null ou mal défini");
				return true;
			}

			// effet réussite est facultatif
			if (effetReussite != null && effetReussite.effetReussiteMalDefini()) {
				logger.debug("Effet réussite est mal défini");
				return true;
			}
		}

		return false;
	}
}
