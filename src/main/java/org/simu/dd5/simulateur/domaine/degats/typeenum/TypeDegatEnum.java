package org.simu.dd5.simulateur.domaine.degats.typeenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.commun.Utils;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum TypeDegatEnum {
	// TODO : est-ce qu'il faut ajouter toutes les croisements ?
	CONTONDANT("contondant"),
	CONTONDANT_MAGIQUE("contondant magique"),
	CONTONDANT_ARGENT("contondant argent"),
	CONTONDANT_ADAMENTIUM("contondant adamentium"),

	TRANCHANT("tranchant"),
	TRANCHANT_MAGIQUE("contondant magique"),
	TRANCHANT_ARGENT("contondant argent"),
	TRANCHANT_ADAMENTIUM("tranchant adamentium"),

	PERFORANT("perforant"),
	PERFORANT_MAGIQUE("contondant magique"),
	PERFORANT_ARGENT("perforant argent"),
	PERFORANT_ADAMENTIUM("perforant adamentium"),

	POISON("poison"),
	NECROTIQUE("nécrotique"),
	MAGIQUE("magique"),
	FORCE("force"),
	RADIANT("radiant"),
	FEU("feu"),
	FROID("froid"),
	FOUDRE("foudre"),
	TONNERRE("tonnerre"),
	ACIDE("acide"),
	PSYCHIQUE("psychique"),

	CPT_NON_MAGIQUE("c-p-t-non-magique"),
	CPT_NON_MAGIQUE_NON_ARGENT("c-p-t-non-magique-non-argent"),
	CPT_NON_ARGENT("c-p-t-non-argent"),
	CPT_NON_MAGIQUE_NON_ADAMENTIUM("c-p-t-non-magique-non-adamentium"),
	CPT_NON_MAGIQUE_NON_ARGENT_NON_FER_FROID("perforants et tranchants issus d'armes non magiques qui ne sont pas plaquées argent ou faite de fer froid"),
	CPT_NON_ADAMENTIUM("c-p-t-non-adamantium"),
	CPT_NON_FER_FROID("c-p-t-non-fer-froid"),
	C_NON_MAGIQUE("c-non-magique"),
	CT_NON_MAGIQUE("c-t-non-magique"),
	T_NON_MAGIQUE("t-non-magique"),
	P_NON_MAGIQUE("p-non-magique"),
	PT_NON_MAGIQUE("p-t-non-magique"),
	PT_NON_ADAMENTIUM("p-t-non-adamantium"),
	PT_NON_ARGENT("p-t-non-argent"),
	PT_NON_MAGIQUE_NON_ARGENT("perforant et tranchant d'attaques non magiques qui ne sont pas en argent"),
	CP_NON_MAGIQUE("c-p-non-magique"),
	PT_NON_MAGIQUE_NON_ADAMENTIUM("perforant et tranchant d'attaques non magiques qui ne sont pas en adamantium"),
	ARME_ARGENT("armes en argent");

	private static final List<TypeDegatEnum> typeDegatsComplexes = Arrays.asList(
			CPT_NON_MAGIQUE, CPT_NON_MAGIQUE_NON_ARGENT, CPT_NON_ARGENT, CPT_NON_MAGIQUE_NON_ADAMENTIUM,
			CPT_NON_MAGIQUE_NON_ARGENT_NON_FER_FROID, CPT_NON_ADAMENTIUM, CPT_NON_FER_FROID, C_NON_MAGIQUE,
			CT_NON_MAGIQUE, T_NON_MAGIQUE, P_NON_MAGIQUE, PT_NON_MAGIQUE, PT_NON_ADAMENTIUM, PT_NON_ARGENT,
			PT_NON_MAGIQUE_NON_ARGENT, CP_NON_MAGIQUE, PT_NON_MAGIQUE_NON_ADAMENTIUM
	);

	private final String type;

	public static TypeDegatEnum fromValeur(String s) {
		if (s.isEmpty()) {
			return null;
		}

		for (TypeDegatEnum e : TypeDegatEnum.values()) {
			// traite les cas : égalités, égalités en enlevant les accents, égalité en enlevant la dernière lettre exemple, "s"
			if (e.getType().equals(s) || Utils.remplacerAccents(e.getType()).equals(s) || e.getType().equals(s.substring(0, s.length() - 1))) {
				return e;
			}
		}

		if (s.startsWith("de ") || s.startsWith("d'")) {
			return fromValeur(s.substring(2).trim());
		}

		return switch (s) {
			// TODO : à nettoyer en modifiant les créatures dans code python
			case "perforant et tranchant provenant d'attaques non magiques qui ne sont pas faites avec des armes en argent",
				 "perforant et tranchant provenant d'attaques non-magiques qui ne sont pas réalisées avec des armes argentées" ->
					PT_NON_MAGIQUE_NON_ARGENT;
			case "perforant et tranchant provenant d'attaques non magiques non effectuées avec des armes en adamantine" ->
					PT_NON_MAGIQUE_NON_ADAMENTIUM;
			case "contondant, perforant et tranchant d'attaques non magiques qui ne sont pas en adamantium" ->
					CPT_NON_MAGIQUE_NON_ADAMENTIUM;
			case "tonnerre (seulement sous sa forme éthérée)" -> TONNERRE;
			case "c-p-t-non-magique (sort peau de pierre)" -> CPT_NON_MAGIQUE;
			default -> throw new IllegalArgumentException("Aucune constante avec la valeur " + s + " n'a été trouvée.");

		};
	}

	public boolean contient(TypeDegatEnum typeDegat) {
		if(!typeDegatsComplexes.contains(typeDegat)) {
			// on ne veut gérer ici que les types de dégâts complexes
			return false;
		}

		return switch (this) {
			case CPT_NON_MAGIQUE -> Arrays.asList(
					CONTONDANT, PERFORANT, TRANCHANT,
					CONTONDANT_ADAMENTIUM, PERFORANT_ADAMENTIUM, TRANCHANT_ADAMENTIUM,
					CONTONDANT_ARGENT, PERFORANT_ARGENT, TRANCHANT_ARGENT
			).contains(typeDegat);

			case CPT_NON_MAGIQUE_NON_ARGENT -> Arrays.asList(
					CONTONDANT, PERFORANT, TRANCHANT,
					CONTONDANT_ADAMENTIUM, PERFORANT_ADAMENTIUM, TRANCHANT_ADAMENTIUM
			).contains(typeDegat);

			case CPT_NON_ARGENT -> Arrays.asList(
					CONTONDANT, PERFORANT, TRANCHANT,
					CONTONDANT_ADAMENTIUM, PERFORANT_ADAMENTIUM, TRANCHANT_ADAMENTIUM,
					CONTONDANT_MAGIQUE, PERFORANT_MAGIQUE, TRANCHANT_MAGIQUE
			).contains(typeDegat);

			case CPT_NON_MAGIQUE_NON_ADAMENTIUM -> Arrays.asList(
					CONTONDANT, PERFORANT, TRANCHANT,
					CONTONDANT_ARGENT, PERFORANT_ARGENT, TRANCHANT_ARGENT
			).contains(typeDegat);

			case C_NON_MAGIQUE -> Arrays.asList(
					CONTONDANT, CONTONDANT_ARGENT, CONTONDANT_ADAMENTIUM
			).contains(typeDegat);

			case CT_NON_MAGIQUE -> Arrays.asList(
					CONTONDANT, CONTONDANT_ARGENT, CONTONDANT_ADAMENTIUM,
					TRANCHANT, TRANCHANT_ARGENT, TRANCHANT_ADAMENTIUM
			).contains(typeDegat);

			default -> {
				System.out.println("--------------- dégât complexe non traité" + typeDegat);
				yield false;
			}
		};
	}
}
