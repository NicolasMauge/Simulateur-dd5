package org.simu.dd5.simulateur.domaine.opposant.typeenum;

import lombok.Getter;

@Getter
public enum CaracteristiqueEnum {
	FOR("force"),
	DEX("dextérité"),
	CON("constitution"),
	INT("intelligence"),
	SAG("sagesse"),
	CHA("charme");

	private final String description;

	CaracteristiqueEnum(String description) {
		this.description = description;
	}
}
