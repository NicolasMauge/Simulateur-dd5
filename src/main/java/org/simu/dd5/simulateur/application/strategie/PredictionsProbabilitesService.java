package org.simu.dd5.simulateur.application.strategie;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.avantages.AvantageService;
import org.simu.dd5.simulateur.application.degats.DegatsService;
import org.simu.dd5.simulateur.application.etats.EtatsService;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.probabilites.Prediction;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PredictionsProbabilitesService {
	private static final Logger logger = LoggerFactory.getLogger(PredictionsProbabilitesService.class);

	private final DegatsService degatsService;
	private final EtatsService etatsService;
	private final AvantageService avantageService;

//	public List<Prediction> predictions(Attaque attaque, Opposant attaquant, Opposant defenseur) {
//		if(attaque.estNonCoherente()) {
//			return null;
//		}
//
//		if (attaquant.estIncapableDAgir()) {
//			return List.of(new Prediction(0, new HashMap<>(), new HashSet<>()));
//		}
//
//		return switch (attaque.estDeQuelType()) {
//			case ATTAQUE_AVEC_TOUCHER -> {
//				List<Prediction> predictionsListe = new ArrayList<>();
//				predictionsListe.add(quellesPredictionsPourUneAttaque(attaque.getTest(), attaque.getEffet().getDegats(), new HashSet<>(), attaquant, defenseur));
//
//				if(attaque.getEffet().getTest() != null) {
//					predictionsListe.add(quellesPredictionsPourUneAttaque(attaque.getEffet().getTest(), attaque., new HashSet<>(), attaquant, defenseur));
//				}
//				yield predictionsListe;
//			}
//			case EVASION -> null;
//			default -> null;
//		};
//		Prediction prediction = quellesPredictionsPourUneAttaque(attaque.getTest(), )
//
//	}

	public Prediction quellesPredictionsPourUneAttaque(DDTestReussite test, Map<TypeDegatEnum, Degats> degats, Set<EtatEnum> etats, Opposant attaquant, Opposant defenseur) {
		return switch (test.estDeQuelType()) {
			case ATTAQUE_AVEC_TOUCHER -> new Prediction(
					quelleProbabiliteAttaqueToucher(test, attaquant, defenseur),
					degatsService.quelsDegatsMoyens(degats, defenseur),
					etats
			);
			case EVASION -> new Prediction(
					quelleProbabiliteEvasion(test, attaquant, defenseur),
					degatsService.quelsDegatsMoyens(degats, defenseur),
					etats
			);
			default -> null;
		};
	}

	private int quelleProbabiliteAttaqueToucher(DDTestReussite test, Opposant attaquant, Opposant defenseur) {
		AvantageEnum avantage = avantageService.syntheseAvantagePourAttaquant(attaquant, defenseur);

		return switch (avantage) {
			case AVANTAGE -> calculProbabiliteAvecAvantage(
					test.getBonusToucher() + attaquant.getBonusDeMaitrise(),
					defenseur.getClasseArmure());

			case DESAVANTAGE -> calculProbabiliteAvecDesavantage(
					test.getBonusToucher() + attaquant.getBonusDeMaitrise(),
					defenseur.getClasseArmure());

			default -> calculProbabilite(
					test.getBonusToucher() + attaquant.getBonusDeMaitrise(),
					defenseur.getClasseArmure());
		};
	}

	private int quelleProbabiliteEvasion(DDTestReussite test, Opposant attaquant, Opposant defenseur) {
		AvantageEnum avantage = defenseur.aUnAvantagePourTestSur(test.getJetSauvegarde().getCompetence());

		// attention, contrairement à ce qui se fait dans les autres fonctions
		// ici, on se positionne du point de vue de l'attaquant
		return switch (avantage) {
			case AVANTAGE -> 100 - calculProbabiliteAvecAvantage(
					defenseur.getValeurCompetence(test.getJetSauvegarde().getCompetence()),
					test.getJetSauvegarde().getDd());

			case DESAVANTAGE -> 100 - calculProbabiliteAvecDesavantage(
					defenseur.getValeurCompetence(test.getJetSauvegarde().getCompetence()),
					test.getJetSauvegarde().getDd());

			default -> 100 - calculProbabilite(
					defenseur.getValeurCompetence(test.getJetSauvegarde().getCompetence()),
					test.getJetSauvegarde().getDd());
		};
	}

	private int calculProbabilite(int bonus, int defense) {
		int seuil = defense - bonus - 1;

		int probabiliteSucces = 100 - 5 * seuil;

		if (probabiliteSucces < 5) {
			probabiliteSucces = 5; // car il est possible d'avoir un 20 qui est un succès automatique
		}

		return probabiliteSucces;
	}

	private int calculProbabiliteAvecAvantage(int bonus, int defense) {
		int seuil = defense - bonus - 1;

		int probabiliteSucces = 100 * 2 * (20 - seuil) / (2 * (20 - seuil) + seuil);

		if (probabiliteSucces < 10) {
			probabiliteSucces = 10; // car il est possible d'avoir un 20 qui est un succès automatique
		}

		return probabiliteSucces;
	}

	private int calculProbabiliteAvecDesavantage(int bonus, int defense) {
		int seuil = defense - bonus - 1;

		int probabiliteSucces = 100 * (20 - seuil) / (20 + seuil);

		if (probabiliteSucces < 2) {
			probabiliteSucces = 2; // car il est possible d'avoir un 20 qui est un succès automatique
		}

		return probabiliteSucces;
	}
}
