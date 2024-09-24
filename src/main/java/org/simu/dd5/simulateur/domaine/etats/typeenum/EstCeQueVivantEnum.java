package org.simu.dd5.simulateur.domaine.etats.typeenum;

import lombok.Getter;

@Getter
public enum EstCeQueVivantEnum {
	VIVANT("vivante"),
	NEUTRALISE("neutralisée");

	private final String statut;

	EstCeQueVivantEnum(String statut) {
		this.statut = statut;
	}
}
