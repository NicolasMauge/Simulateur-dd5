package org.simu.dd5.simulateur.domaine.etats.typeenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.commun.Utils;

@AllArgsConstructor
@Getter
public enum EtatEnum {
	// on se positionne d'un point de vue d'une attaque
	// lors d'une attaque, est-ce que la condition affecte l'attaquant ?
	// A_TERRE : on considère que l'assaillant est à < 1,5m
	EMPOISONNE("empoisonné"),
	AVEUGLE("aveuglé"),
	ASSOURDI("assourdi"),
	PARALYSE("paralysé"),
	ENTRAVE("entravé"),
	ETOURDI("étourdi"),
	A_TERRE("à terre"),
	PETRIFIE("pétrifié"),
	CHARME("charmé"),
	INCONSCIENT("inconscient"),
	EFFRAYE("effrayé"),
	AGRIPPE("agrippé"),
	EPUISE("épuisé"),
	INVISIBLE("invisible"),
	MALADIE("maladie"),
	REPOUSSE("repoussé"),
	AFFAIBLI("affaibli"),
	NEUTRALISE("neutralisé"),
	TOMBER_A_ZERO_PV("tomber à 0 point de vie"),
	SANS_CONDITION("pas de condition particulière");

	private final String etat;

	public static EtatEnum fromValeur(String s) {
		if(s.isEmpty()) {
			return null;
		}

		for (EtatEnum e : EtatEnum.values()) {
			if (e.getEtat().equals(s) || Utils.remplacerAccents(e.getEtat()).equals(s) || e.getEtat().equals(s.substring(0,s.length()-1))) {
				return e;
			}
		}

		return switch (s) {
			case "a-terre" -> A_TERRE;
			case "terrorise", "terrorisé" -> EFFRAYE;
			case "empoigné", "empoigne" -> AGRIPPE;
			case "épuisement" -> EPUISE;
			default -> throw new IllegalArgumentException("Aucune constante avec la valeur " + s + " n'a été trouvée.");
		};
	}
}
