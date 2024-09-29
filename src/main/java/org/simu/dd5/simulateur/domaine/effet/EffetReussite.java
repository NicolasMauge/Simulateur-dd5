package org.simu.dd5.simulateur.domaine.effet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class EffetReussite {
	private static final Logger logger = LoggerFactory.getLogger(EffetReussite.class);

	private String degatsMinimum;
	private Map<TypeDegatEnum, Degats> degats;

	public boolean effetReussiteMalDefini() {
		if(degatsMinimum != null && !degatsMinimum.equals("MOITIE")) {
			logger.debug("Si les dégâts minimum sont remplis, ils doivent contenir MOITIE");
			return true;
		}

		if(degatsMinimum == null && degats == null) {
			logger.debug("Au moins degats minimum ou degats doit être rempli");
			return true;
		}

		return false;
	}
}
