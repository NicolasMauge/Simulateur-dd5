package org.simu.dd5.simulateur.application.degats;

import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DegatsService {
	public SousResultatAttaque quelsSontLesDegatsRecus(ResultatTestDDEnum resultatTest, List<Degats> degats, Opposant defenseur, boolean degatsAMultiplierParDeux) {
		// on récupère les dégâts
		Map<TypeDegatEnum, Integer> degatsParType = degats
				.stream()
				.collect(Collectors.toMap(
								Degats::getTypeDegats,
								Degats::getValeurRandom
						)
				);

		if(degatsAMultiplierParDeux) {
			degatsParType = degatsParType.entrySet()
					.stream()
					.collect(Collectors.toMap(
							Map.Entry::getKey,
							this::multipliePar2Value));
		}

		// on modifie ces dégâts en fonction des particularités du défenseur
		Map<TypeDegatEnum, Integer> degatsParTypeAjuste = degatsParType.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						defenseur::quelDegatAjuste
				));

		return new SousResultatAttaque(
				resultatTest,
				degatsParType,
				degatsParTypeAjuste,
				new HashMap<>()
		);
	}

	private Integer multipliePar2Value(Map.Entry<TypeDegatEnum, Integer> entry) {
		return entry.getValue() * 2;
	}
}
