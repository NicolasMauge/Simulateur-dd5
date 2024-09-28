package org.simu.dd5.simulateur.application.commun;

import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;

public class AttaqueCommun {
	public static boolean estAttaqueAvecToucher(Attaque attaque) {
		return attaque.getTest() != null && attaque.getTest().getBonusToucher() != null;
	}

	public static boolean estAttaqueAvecToucher(DDTestReussite testReussite) {
		return testReussite != null && testReussite.getBonusToucher() != null;
	}
}
