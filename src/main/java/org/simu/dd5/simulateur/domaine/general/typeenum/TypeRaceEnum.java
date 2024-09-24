package org.simu.dd5.simulateur.domaine.general.typeenum;

import lombok.Getter;

@Getter
public enum TypeRaceEnum {
	HUMANOIDE("Humanoïde"),
	GOBELOIDE("Gobeloïde"),
	AUTRE("Autre");

	private final String nom;

	TypeRaceEnum(String nom){
		this.nom = nom;
	}
}
