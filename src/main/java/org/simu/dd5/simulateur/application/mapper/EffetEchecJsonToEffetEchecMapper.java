package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.effet.EffetEchec;
import org.simu.dd5.simulateur.domaine.effet.EffetEchecJson;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class EffetEchecJsonToEffetEchecMapper {
	private final DegatsJsonToDegatsMapper degatsJsonToDegatsMapper;
	private final EtatsFromString etatsFromString;

	public EffetEchec mapToEffetEchec(EffetEchecJson effetEchecJson) {
		if (effetEchecJson == null) {
			return null;
		}

		if(effetEchecJson.getDegats() == null && effetEchecJson.getEtat() == null) {
			return null;
		}

		Map<TypeDegatEnum, Degats> degats = degatsJsonToDegatsMapper.mapToListeDegats(effetEchecJson.getDegats());
		Set<EtatEnum> etatsListe = etatsFromString.getListeEtatFromStringListe(effetEchecJson.getEtat());

		if(degats == null && etatsListe == null) {
			return null;
		}

		return new EffetEchec(
				degats,
				etatsListe
		);
	}
}
