package org.simu.dd5.simulateur.domaine.commun;

import java.text.Normalizer;

public class Utils {
	public static String remplacerAccents(String chaine) {
		// Normaliser la chaîne (NFD décompose les caractères accentués)
		String normalized = Normalizer.normalize(chaine, Normalizer.Form.NFD);
		// Ne garder que les caractères ASCII de base en supprimant les diacritiques
		return normalized.replaceAll("\\p{M}", "");
	}
}
