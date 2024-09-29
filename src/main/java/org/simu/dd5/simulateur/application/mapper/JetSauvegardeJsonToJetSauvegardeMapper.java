package org.simu.dd5.simulateur.application.mapper;

import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.simu.dd5.simulateur.domaine.touche.JetSauvegarde;
import org.simu.dd5.simulateur.domaine.touche.JetSauvegardeJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JetSauvegardeJsonToJetSauvegardeMapper {
	private static final Logger logger = LoggerFactory.getLogger(JetSauvegardeJsonToJetSauvegardeMapper.class);

	public JetSauvegarde mapToJetSauvegarde(JetSauvegardeJson jetSauvegardeJson) {
		if (jetSauvegardeJson == null) {
			return null;
		}

		try {
			CompetenceEnum competence = CompetenceEnum.fromValeur(jetSauvegardeJson.getCompetence().toLowerCase());
			Integer dd = ConversionsUtils.integerFromString(jetSauvegardeJson.getDd());

			if (competence != null && dd != null) {
				return new JetSauvegarde(competence, dd);
			}
		} catch(IllegalArgumentException e) {
			logger.debug("La compétence ({}) n'existe pas", jetSauvegardeJson.getCompetence().toLowerCase());
			return null;
		}
		logger.debug("La compétence ({}) ou le degré de difficulté ({}) ne sont pas convertible", jetSauvegardeJson.getCompetence(), jetSauvegardeJson.getDd());
		return null;
	}
}
