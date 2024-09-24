package org.simu.dd5.simulateur.domaine.opposant.typeenum;

import lombok.Getter;

@Getter
public enum AvantageEnum {
	AVANTAGE("avantagé"),
	DESAVANTAGE("désavantagé"),
	NEUTRE("ni avantagé, ni désavantagé"),
	AUCUNE_ACTION("aucune action possible");

	private final String typeAvantage;

	AvantageEnum(String typeAvantage) {
		this.typeAvantage = typeAvantage;
	}
}
