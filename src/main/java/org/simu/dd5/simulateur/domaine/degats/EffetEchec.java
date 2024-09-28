package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
public class EffetEchec {
	private static final Logger logger = LoggerFactory.getLogger(EffetEchec.class);

	private Map<TypeDegatEnum, Degats> degats;
	private Set<EtatEnum> etatSet;

	public boolean effetEchecMalDefini() {
		if(degats == null && (etatSet == null || etatSet.isEmpty())) {
			logger.debug("Au moins les dégats ou la liste des états doit être rempli");
			return true;
		}

		return false;
	}
}
