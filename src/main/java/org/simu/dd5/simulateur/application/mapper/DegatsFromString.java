package org.simu.dd5.simulateur.application.mapper;

import org.simu.dd5.simulateur.domaine.aleatoire.NombreDeDes;
import org.simu.dd5.simulateur.domaine.aleatoire.typeenum.TypeDesEnum;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.DegatsJson;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeEffetDegatsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DegatsFromString {
	private static final Logger logger = LoggerFactory.getLogger(DegatsFromString.class);

	public Map<TypeDegatEnum, TypeEffetDegatsEnum> getEffetDegatsEnFonctionType(String resistances,
																				String vulnerabilites,
																				String immunites) {
		Map<TypeDegatEnum, TypeEffetDegatsEnum> dictionnaire = new HashMap<>();

		getListeDegatsFromString(resistances).forEach(r -> dictionnaire.put(r, TypeEffetDegatsEnum.RESISTANCE));
		getListeDegatsFromString(vulnerabilites).forEach(r -> dictionnaire.put(r, TypeEffetDegatsEnum.VULNERABILITE));
		getListeDegatsFromString(immunites).forEach(r -> dictionnaire.put(r, TypeEffetDegatsEnum.IMMUNITE));

		return dictionnaire;
	}

	private List<TypeDegatEnum> getListeDegatsFromString(String degatsString) {
		if (degatsString == null) {
			return new ArrayList<>();
		}

		return Arrays.stream(degatsString.trim().split("\\|"))
				.map(this::convertitStringToTypeDegatEnum)
				.filter(Objects::nonNull)
				.toList();
	}

	public TypeDegatEnum convertitStringToTypeDegatEnum(String s) {
		String sNettoye = s.toLowerCase().trim();

		try {
			return TypeDegatEnum.fromValeur(sNettoye);
		} catch (IllegalArgumentException e) {
			logger.debug("Erreur de conversion de '{}' en TypeDegatEnum", sNettoye);
		}

		return null;
	}

	public Map.Entry<TypeDegatEnum, Degats> mapToDegats(DegatsJson degatsJson) {
		TypeDegatEnum typeDegat = convertitStringToTypeDegatEnum(degatsJson.getTypeDegats());

		if(typeDegat == null) {
			logger.debug("Le type de dégât ({}) n'a pas été reconnu", degatsJson.getTypeDegats());
			return null;
		}

		Integer valeurMoyenneDegat = valeurMoyenneDegat(degatsJson.getValeurDegats());

		if (valeurMoyenneDegat == null) {
			logger.debug("La valeur moyenne des dégâts est null : {}", degatsJson);
			return null;
		}

		NombreDeDes nombreDeDes = getNombreDeDes(degatsJson.getValeurDegats().toLowerCase());

		return Map.entry(typeDegat, new Degats(valeurMoyenneDegat, nombreDeDes));
	}

	private NombreDeDes getNombreDeDes(String d) {
		// 5 (1d4 + 3)
		int positionParentheseGauche = d.indexOf("(");

		if (positionParentheseGauche == -1) {
			return null;
		}

		int positionParentheseDroite = d.indexOf(")");
		int positionD = d.indexOf("d");

		if(positionParentheseDroite == -1 || positionD == -1) {
			return null;
		}

		// 5 (1d4 + 3) -> 5
		Integer nombreDes = ConversionsUtils.integerFromString(d.substring(positionParentheseGauche+1, positionD).trim());

		if(nombreDes == null) {
			return null;
		}

		// 5 (1d4 + 3) -> d4 et 3
		int positionPlus = d.indexOf("+");
		int positionMoins = d.indexOf("-");

		String typeDeString;
		String complementString;
		if(positionPlus != -1) {
			typeDeString = d.substring(positionD, positionPlus).trim().toLowerCase();
			complementString = d.substring(positionPlus+1, positionParentheseDroite).trim();
		} else if(positionMoins != -1) {
			typeDeString = d.substring(positionD, positionMoins).trim().toLowerCase();
			complementString = "-" + d.substring(positionMoins+1, positionParentheseDroite).trim();
		}
		else {
			typeDeString = d.substring(positionD, positionParentheseDroite).trim().toLowerCase();
			complementString = null;
		}

		TypeDesEnum typeDe = TypeDesEnum.fromValeur(typeDeString);

		Integer complement;
		if(complementString != null) {
			complement = ConversionsUtils.integerFromString(complementString);
			if(complement == null) {
				complement = 0;
			}
		} else {
			complement = 0;
		}

		return new NombreDeDes(nombreDes, typeDe, complement);
	}

	private Integer valeurMoyenneDegat(String d) {
		int position = d.indexOf("(");

		if (position != -1) {
			String degatsMoyensToConvert = d.substring(0, position).trim();

			return ConversionsUtils.integerFromString(degatsMoyensToConvert);
		}

		return ConversionsUtils.integerFromString(d);
	}
}
