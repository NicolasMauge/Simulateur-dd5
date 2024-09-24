package org.simu.dd5.simulateur.domaine.resultat;

import lombok.*;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResultatAttaque {
	private SousResultatAttaque degatsEtConditionAttaqueToucher;
	private SousResultatAttaque degatsEtConditionEvasion;

	public static ResultatAttaque EN_ECHEC(ResultatTestDDEnum resultat) {
		return new ResultatAttaque(
				new SousResultatAttaque(resultat, null, null, null),
				null
				);
	}

	public ResultatAttaque(SousResultatAttaque degatsEtConditionAttaqueToucher) {
		this.degatsEtConditionAttaqueToucher = degatsEtConditionAttaqueToucher;
	}

	public ResultatAttaque ajouteResultatAttaqueEvasion(SousResultatAttaque degatsEtConditionEvasion) {
		if(degatsEtConditionEvasion == null) {
			return this;
		}

		return new ResultatAttaque(degatsEtConditionAttaqueToucher, degatsEtConditionEvasion);
	}
}