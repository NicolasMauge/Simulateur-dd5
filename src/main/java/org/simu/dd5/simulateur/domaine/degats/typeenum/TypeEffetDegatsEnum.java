package org.simu.dd5.simulateur.domaine.degats.typeenum;

import lombok.Getter;

@Getter
public enum TypeEffetDegatsEnum {
	RESISTANCE("résistance"),
	VULNERABILITE("vulnérabilité"),
	IMMUNITE("immunité"),
	EFFET_NORMAL("effet normal");

	private final String typologie;

	TypeEffetDegatsEnum(String typologie) {
		this.typologie = typologie;
	}
}
