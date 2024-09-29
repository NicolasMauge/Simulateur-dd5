package org.simu.dd5.simulateur.application.mapper;

import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EtatsFromString {
	private static final Logger logger = LoggerFactory.getLogger(EtatsFromString.class);

	public Set<EtatEnum> getListeEtatFromStringListe(List<String> etatListe) {
		if(etatListe == null) {
			return null;
		}

		return etatListe.stream().map(this::getEtatFromString).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	private EtatEnum getEtatFromString(String etat) {
		if(etat == null) {
			return null;
		}

		try {
			return EtatEnum.fromValeur(etat.trim());
		} catch (IllegalArgumentException e) {
			logger.debug("L'état {} n'a pas été converti", etat);
			return null;
		}
	}
}
