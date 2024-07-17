package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum CompetenceEnum {
	ARCANE("Arcane"),
	MEDECINE("Médecine"),
	ACROBATIE("Acrobatie"),
	;

	private final String nomCompetence;

	CompetenceEnum(String nomCompetence) {
		this.nomCompetence = nomCompetence;
	}

}
