package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.etats.SituationOpposant;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.simu.dd5.simulateur.domaine.opposant.typeenum.CaracteristiqueEnum.*;

@Component
@AllArgsConstructor
public class OpposantJsonToOpposantMapper {
	private static final Logger logger = LoggerFactory.getLogger(OpposantJsonToOpposantMapper.class);

	private final CaracteristiquesFromString caracteristiquesFromString;
	private final CompetencesFromString competencesFromString;
	private final DegatsFromString degatsFromString;
	private final AttaqueJsonToAttaqueMapper mapper;

	public Opposant mapToOpposant(OpposantJson input) {
		Opposant output = new Opposant(
				input.getNomCreature(),
				null,
				getCaFromString(input.getCa()),
				getVieFromString(input.getPointsDeVie()),
				input.getPointsDeVie(),
				input.getVitesse(),
				caracteristiquesFromString.getCaracteristiquesFromStrings(input),
				competencesFromString.getCompetenceListFromString(input.getCompetences()),
				null,
				degatsFromString.getEffetDegatsEnFonctionType(input.getResistancesAuxDegats(), input.getVulnerabilitesAuxDegats(), input.getImmunitesAuxDegats()),
				getEtatsFromString(input.getImmunitesAuxEtats()),
				mapper.mapToListeAttaque(input.getAttaqueListe()),
				new SituationOpposant(9999, new HashMap<>(), new HashMap<>())
		);

		// ajouter les modificateurs de caractéristiques aux compétences
		Arrays.asList(FOR, DEX, CON, INT, SAG, CHA)
				.forEach(c -> output
						.getCompetenceList()
						.put(CompetenceEnum.fromCaracteristique(c), output.getValeurCaracteristique(c))
				);


		output.getSituationOpposant().setVie(output.getPointDeVie()!=null?output.getPointDeVie():-1);

		return output;
	}


	private Integer getCaFromString(String ca) {
		int position = ca.indexOf("(");

		if (position != -1) {
			return integerFromString(ca.substring(0, position).trim());
		}

		return integerFromString(ca);
	}


	private List<EtatEnum> getEtatsFromString(String immunitesAuxEtats) {
		if (immunitesAuxEtats == null || immunitesAuxEtats.isEmpty()) {
			return new ArrayList<>();
		}

		// a-terre|charme|empoigne|empoisonne|epuise|entrave|inconscient|paralyse|petrifie
		return Arrays.stream(immunitesAuxEtats.split("\\|"))
				.map(this::convertitStringToEtatEnum)
				.filter(Objects::nonNull)
				.toList();
	}

	private EtatEnum convertitStringToEtatEnum(String s) {
		String sNettoye = s.toLowerCase().trim();

		try {
			return EtatEnum.fromValeur(sNettoye);
		} catch (IllegalArgumentException e) {
			logger.warn("Erreur de conversion de {} en EtatEnum", sNettoye);
		}

		return null;
	}

	private Integer getVieFromString(String vie) {
		int position = vie.indexOf("(");

		if (position != -1) {
			String vieToConvert = vie.substring(0, position).trim();

			return integerFromString(vieToConvert);
		}

		logger.warn("Il n'y a pas de parenthèse dans {}", vie);
		return null;
	}

	private Integer integerFromString(String s) {
		if (s == null) {
			return null;
		}

		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			logger.warn("Erreur de conversion de {} en Integer", s);
			return null;
		}
	}
}
