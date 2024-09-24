package org.simu.dd5.simulateur.domaine.opposant.typeenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.commun.Utils;

@AllArgsConstructor
@Getter
public enum CompetenceEnum {
	ATHLETISME("athlétisme"), // FOR

	ACROBATIE("acrobatie"), // DEX
	DISCRETION("discrétion"),
	FURTIVITE("furtivité"),
	ESCAMOTAGE("escamotage"),
	EVASION("évasion"),

	ARCANES("arcanes"), // INT
	HISTOIRE("histoire"),
	INVESTIGATION("investigation"),
	NATURE("nature"),
	RELIGION("religion"),

	DRESSAGE("dressage"), // SAG
	DISCERNEMENT("discernement"),
	MEDECINE("médecine"),
	PERCEPTION("perception"),
	SURVIE("survie"),
	INTUITION("intuition"),
	PERSPICACITE("perspicacité"),

	INTIMIDATION("intimidation"), // CHA
	PERSUASION("persuasion"),
	REPRESENTATION("représentation"),
	TROMPERIE("tromperie"),
	SUPERCHERIE("supercherie"),

	FOR("force"),
	DEX("dextérité"),
	CON("constitution"),
	INT("intelligence"),
	SAG("sagesse"),
	CHA("charme");

	private final String nomCompetence;

	public static CompetenceEnum fromCaracteristique(CaracteristiqueEnum c) {
		return switch(c) {
			case FOR -> FOR;
			case DEX -> DEX;
			case CON -> CON;
			case INT -> INT;
			case SAG -> SAG;
			case CHA -> CHA;
		};
	}

	public CaracteristiqueEnum quelSubstitut() {
		return switch(this) {
			case ATHLETISME, FOR -> CaracteristiqueEnum.FOR;
			case ACROBATIE, DISCRETION, ESCAMOTAGE, FURTIVITE, EVASION, DEX -> CaracteristiqueEnum.DEX;
			case ARCANES, HISTOIRE, INVESTIGATION, NATURE, RELIGION, INT -> CaracteristiqueEnum.INT;
			case DRESSAGE, DISCERNEMENT, MEDECINE, PERCEPTION, SURVIE, INTUITION, PERSPICACITE, SAG -> CaracteristiqueEnum.SAG;
			case INTIMIDATION, PERSUASION, REPRESENTATION, TROMPERIE, SUPERCHERIE, CHA -> CaracteristiqueEnum.CHA;
			case CON -> CaracteristiqueEnum.CON;
		};
	}

	public static CompetenceEnum fromValeur(String s) {
		for (CompetenceEnum e : CompetenceEnum.values()) {
			if (e.getNomCompetence().equals(s) || Utils.remplacerAccents(e.getNomCompetence()).equals(s)) {
				return e;
			}
		}

		return switch (s) {
			case "arcane" -> ARCANES;
			case "acrobaties" -> ACROBATIE;
			case "fuir" -> EVASION;
			case "force" -> FOR;
			case "dextérité" -> DEX;
			case "constitution" -> CON;
			case "intelligence" -> INT;
			case "d'intelligence" -> INT;
			case "sagesse" -> SAG;
			case "charisme" -> CHA;
			case "survival" -> SURVIE;
			case "history" -> HISTOIRE;
			case "libérer" -> EVASION;

			default -> throw new IllegalArgumentException("Aucune constante avec la valeur " + s + " n'a été trouvée.");
		};

	}
}
