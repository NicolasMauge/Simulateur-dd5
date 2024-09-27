package org.simu.dd5.simulateur.application.elo;

import org.simu.dd5.simulateur.application.round.RoundService;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClassementEloService {
	private static final Logger logger = LoggerFactory.getLogger(RoundService.class);

	private static final int K = 32; // Facteur K utilisé dans le calcul Elo

	public void calculNouveauELO(Opposant opposantA, Opposant opposantB, UUID gagnant) {
		double expectedScore1 = expectedScore(opposantA.getClassementELO(), opposantB.getClassementELO());
		double expectedScore2 = expectedScore(opposantB.getClassementELO(), opposantA.getClassementELO());

		int score1 = gagnant == opposantA.getUuid() ? 1 : 0;
		int score2 = gagnant == opposantB.getUuid() ? 1 : 0;

		if(score1 == 0 && score2 == 0) {
			logger.warn("L'uuid ({}) n'était pas parmi les uuid des opposants", gagnant);
			return;
		}

		int nouveauClassementOpposantA = calculateNewRating(opposantA.getClassementELO(), score1, expectedScore1);
		int nouveauClassementOpposantB = calculateNewRating(opposantB.getClassementELO(), score2, expectedScore2);

		opposantA.setClassementELO(nouveauClassementOpposantA);
		opposantB.setClassementELO(nouveauClassementOpposantB);

		logger.debug("{} a pour nouveau ELO : {}", opposantA.getNom(), opposantA.getClassementELO());
		logger.debug("{} a pour nouveau ELO : {}", opposantB.getNom(), opposantB.getClassementELO());
	}

	private double expectedScore(int rating1, int rating2) {
		return 1.0 / (1 + Math.pow(10, (rating2 - rating1) / 400.0));
	}

	private int calculateNewRating(int currentRating, int score, double expectedScore) {
		return (int) (currentRating + K * (score - expectedScore));
	}
}
