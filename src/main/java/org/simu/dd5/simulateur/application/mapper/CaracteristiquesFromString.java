package org.simu.dd5.simulateur.application.mapper;

import org.simu.dd5.simulateur.domaine.opposant.Caracteristique;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CaracteristiquesFromString {
	private static final Logger logger = LoggerFactory.getLogger(CaracteristiquesFromString.class);

	public Caracteristique getCaracteristiquesFromStrings(OpposantJson opposant) {
		Integer force = convertFromString(opposant.getForce());
		Integer dexterite = convertFromString(opposant.getDexterite());
		Integer constitution = convertFromString(opposant.getConstitution());
		Integer intelligence = convertFromString(opposant.getIntelligence());
		Integer sagesse = convertFromString(opposant.getSagesse());
		Integer charisme = convertFromString(opposant.getCharisme());

		return new Caracteristique(
				force,
				modificateurDe(force),
				dexterite,
				modificateurDe(dexterite),
				constitution,
				modificateurDe(constitution),
				intelligence,
				modificateurDe(intelligence),
				sagesse,
				modificateurDe(sagesse),
				charisme,
				modificateurDe(charisme)
		);
	}

	private Integer modificateurDe(Integer caracteristique) {
		if(caracteristique==null) {
			return null;
		}
		return caracteristique/2 - 5;
	}

	private Integer convertFromString(String caracteristique) {
		if(caracteristique==null) {
			return null;
		}

		int position = caracteristique.indexOf("(");

		if(position != -1) {
			String caracteristiqueToConvert = caracteristique.substring(0,position).trim();

			try {
				return Integer.parseInt(caracteristiqueToConvert);
			} catch (NumberFormatException e) {
				logger.warn("Erreur de conversion de {} en Integer", caracteristiqueToConvert);
				return null;
			}
		}

		try {
			return Integer.parseInt(caracteristique);
		} catch (NumberFormatException e) {
			logger.warn("Erreur de conversion de {} en Integer", caracteristique);
			return null;
		}
	}
}
