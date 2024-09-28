package org.simu.dd5.simulateur.application.degats;

import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.SousResultatAttaque;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DegatsService {
	private static final Logger logger = LoggerFactory.getLogger(DegatsService.class);

	public SousResultatAttaque quelDegats(ResultatTestDDEnum resultatTest, Map<TypeDegatEnum, Degats> degats, Opposant defenseur, boolean degatsAMultiplierParDeux) {
		if(degats == null || degats.isEmpty()) {
			logger.warn("Les dégâts sont null");
			return null;
		}

		// on récupère les dégâts
		Map<TypeDegatEnum, Integer> degatsParType = degats.entrySet()
				.stream()
				.collect(Collectors.toMap(
								Map.Entry::getKey,
								this::getValeurRandom));

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

	private Integer getValeurRandom(Map.Entry<TypeDegatEnum, Degats> entry){
		return entry.getValue().getValeurRandom();
	}

	private Integer getValeurMoyenne(Map.Entry<TypeDegatEnum, Degats> entry) {
		return entry.getValue().getValeurMoyenne();
	}

	public Map<TypeDegatEnum, Integer> quelsDegatsMoyens(Map<TypeDegatEnum, Degats> degats, Opposant defenseur) {
		if(degats == null) {
			logger.debug("Il n'y a pas de dégât associés à l'attaque");
			return new HashMap<>();
		}

		// on récupère les dégâts
		Map<TypeDegatEnum, Integer> degatsParType = degats.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						this::getValeurMoyenne));

		// on modifie ces dégâts en fonction des particularités du défenseur
		return degatsParType.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						defenseur::quelDegatAjuste
				));
	}
}
