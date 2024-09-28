package org.simu.dd5.simulateur.domaine.touche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@AllArgsConstructor
@ToString
public class JetSauvegarde {
	private static final Logger logger = LoggerFactory.getLogger(JetSauvegarde.class);

	private CompetenceEnum competence;
	private int dd;

	protected boolean jetSauvegardeMalDefini() {
		if(dd == 0 || competence == null) {
			logger.debug("Un jet de sauvegarde doit avoir un degré de difficulté (DD) et une compétence");
			return true;
		}

		return false;
	}
}
