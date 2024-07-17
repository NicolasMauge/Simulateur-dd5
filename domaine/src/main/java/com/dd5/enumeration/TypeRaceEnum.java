package com.dd5.enumeration;

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
