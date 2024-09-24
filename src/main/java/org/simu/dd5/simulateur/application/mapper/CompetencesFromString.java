package org.simu.dd5.simulateur.application.mapper;

import org.simu.dd5.simulateur.domaine.opposant.Competence;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CompetencesFromString {
	private static final Logger logger = LoggerFactory.getLogger(CompetencesFromString.class);

	public Map<CompetenceEnum, Integer> getCompetenceListFromString(String competencesString) {
		if (competencesString == null || competencesString.isEmpty()) {
			return new HashMap<>();
		}

		Map<CompetenceEnum, Integer> competenceMap = new HashMap<>();
		// Perception +5, Discrétion +7, Survie +5
		Arrays.stream(competencesString.split(","))
				.map(c -> c.trim().split(" "))
				.map(parts -> {
					Integer bonus = convertitStringToInteger(parts[1]);
					if(bonus == null) {
						System.out.println(Arrays.stream(parts).toList());
					}
					CompetenceEnum competenceEnum = convertitStringToCompetenceEnum(parts[0]);

					return new Competence(competenceEnum, bonus);
				})
				.filter(c -> c.getCompetence() != null && c.getBonus() != null)
				.forEach(c -> competenceMap.put(c.getCompetence(), c.getBonus()));

		return competenceMap;
	}

	private Integer convertitStringToInteger(String s) {
		try {
			return Integer.valueOf(s);
		} catch (IllegalArgumentException e) {
			logger.warn("Erreur de conversion de {} en Integer", s);
		}

		return null;
	}

	private CompetenceEnum convertitStringToCompetenceEnum(String s) {
		try {
			return CompetenceEnum.fromValeur(s.toLowerCase());
		} catch (IllegalArgumentException e) {
			logger.warn("Erreur de conversion de {} en CompetenceEnum", s);
		}

		return null;
	}
}
