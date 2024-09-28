package org.simu.dd5.simulateur.application.etats;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.Effet;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class EtatsService {
	private static final Logger logger = LoggerFactory.getLogger(EtatsService.class);

	public Set<EtatEnum> getEtatsFromEffet(Effet effet) {
		if(effet == null) {
			logger.debug("Il n'y a pas d'effet dans l'attaque");
			return new HashSet<>();
		}

		if(effet.getEffetEchec() == null) {
			logger.debug("Il n'y a pas d'effet échec dans l'attaque");
			return new HashSet<>();
		}

		if(effet.getEffetEchec().getEtatSet() == null || effet.getEffetEchec().getEtatSet().isEmpty()) {
			logger.debug("Il n'y a pas d'état associé à l'attaque");
			return new HashSet<>();
		}

		return effet.getEffetEchec().getEtatSet();
	}
}
