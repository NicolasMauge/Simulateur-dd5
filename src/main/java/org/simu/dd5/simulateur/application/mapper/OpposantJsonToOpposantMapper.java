package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
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
				UUID.randomUUID(),
				input.getNomCreature(),
				null,
				getCaFromString(input.getCa()),
				getVieFromString(input.getPointsDeVie()),
				input.getPointsDeVie(),
				input.getVitesse(),
				caracteristiquesFromString.getCaracteristiquesFromStrings(input),
				competencesFromString.getCompetenceListFromString(input.getCompetences()),
				Objects.requireNonNullElse(ConversionsUtils.integerFromString(input.getBonusDeMaitrise()), 0),
				degatsFromString.getEffetDegatsEnFonctionType(input.getResistancesAuxDegats(), input.getVulnerabilitesAuxDegats(), input.getImmunitesAuxDegats()),
				getEtatsFromString(input.getImmunitesAuxEtats()),
				convertDangerosite(input.getDangerosite()),
				mapper.mapToListeAttaque(input.getAttaqueListe()),
				null,
				Opposant.INITIAL_ELO
		);

		// ajouter les modificateurs de caractéristiques aux compétences
		Arrays.asList(FOR, DEX, CON, INT, SAG, CHA)
				.forEach(c -> output
						.getCompetenceList()
						.put(CompetenceEnum.fromCaracteristique(c), output.getValeurCaracteristique(c))
				);

		output.reinitialiseSituation();

		return output;
	}



	private int convertDangerosite(String dangerosite) {
		int position = dangerosite.indexOf("(");

		if(position != -1) {
			dangerosite = dangerosite.substring(0, position).trim();
		}

		switch (dangerosite) {
            case "1/8", "0.125" -> {
                return 1;
            }
            case "1/4", "0.25" -> {
                return 2;
            }
            case "1/2", "0.5" -> {
                return 4;
            }

			default -> {
				Integer dangerositeConvertie = ConversionsUtils.integerFromString(dangerosite);
				if(dangerositeConvertie != null) {
					return 8*dangerositeConvertie;
				}
				else {
					return -1;
				}
			}
        }


    }

	private Integer getCaFromString(String ca) {
		int position = ca.indexOf("(");

		if (position != -1) {
			return ConversionsUtils.integerFromString(ca.substring(0, position).trim());
		}

		return ConversionsUtils.integerFromString(ca);
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
			logger.debug("Erreur de conversion de {} en EtatEnum", sNettoye);
		}

		return null;
	}

	private Integer getVieFromString(String vie) {
		int position = vie.indexOf("(");

		if (position != -1) {
			String vieToConvert = vie.substring(0, position).trim();

			return ConversionsUtils.integerFromString(vieToConvert);
		}

		logger.debug("Il n'y a pas de parenthèse dans {}", vie);
		return null;
	}
}
