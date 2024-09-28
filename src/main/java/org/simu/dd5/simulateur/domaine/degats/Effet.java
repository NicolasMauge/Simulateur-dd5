package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Getter
@AllArgsConstructor
@ToString
public class Effet {
	private static final Logger logger = LoggerFactory.getLogger(Effet.class);

	private Map<TypeDegatEnum, Degats> degats;
	private DDTestReussite test;
	private EffetEchec effetEchec;
	private EffetReussite effetReussite;
	// TODO : est-ce qu'il peut y avoir des états ? modifier effetMalDefini dans ce cas

	public boolean effetMalDefini() {
		if(test != null && test.testMalDefini()) {
			return true;
		}

		if(test != null && (effetEchec == null || effetEchec.effetEchecMalDefini())) {
			logger.debug("Effet échec est null ou mal défini");
			return true;
		}

		if(test != null && (effetReussite == null || effetReussite.effetReussiteMalDefini())) {
			logger.debug("Effet réussite est null ou mal défini");
			return true;
		}

		return false;
	}
}
