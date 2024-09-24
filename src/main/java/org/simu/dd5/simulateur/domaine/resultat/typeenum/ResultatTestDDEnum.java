package org.simu.dd5.simulateur.domaine.resultat.typeenum;

import lombok.Getter;

@Getter
public enum ResultatTestDDEnum {
	REUSSITE_CRITIQUE("réussite critique"),
	REUSSITE("réussite"),
	ECHEC("échec"),
	ECHEC_CRITIQUE("échec critique");

	private final String resultatTest;

	ResultatTestDDEnum(String resultatTest) {
		this.resultatTest = resultatTest;
	}

	public boolean estReussie() {
		return this.equals(REUSSITE_CRITIQUE) || this.equals(REUSSITE);
	}
}
