package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussiteJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DDTestReussiteJsonToDDTestReussiteMapper {
	private static final Logger logger = LoggerFactory.getLogger(DDTestReussiteJsonToDDTestReussiteMapper.class);

	private final JetSauvegardeJsonToJetSauvegardeMapper jetSauvegardeJsonToJetSauvegardeMapper;

	public DDTestReussite mapToDDTestReussite(DDTestReussiteJson ddTestReussiteJson) {
		if (ddTestReussiteJson == null) {
			return null;
		}
		return new DDTestReussite(
				ddTestReussiteJson.getTypeAttaque(),
				ddTestReussiteJson.getTypeArme(),
				ddTestReussiteJson.getTypeDistance(),
				bonusToucherFromString(ddTestReussiteJson.getBonusToucher()),
				jetSauvegardeJsonToJetSauvegardeMapper.mapToJetSauvegarde(ddTestReussiteJson.getJetSauvegarde())
		);
	}

	private Integer bonusToucherFromString(String b) {
		return ConversionsUtils.integerFromString(b);
	}
}
