package org.simu.dd5.simulateur.exposition;

import lombok.AllArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.simu.dd5.simulateur.application.analyse.LogarithmicRegression;
import org.simu.dd5.simulateur.application.analyse.OutlierDetection;
import org.simu.dd5.simulateur.application.attaque.ChoixAttaque;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.simu.dd5.simulateur.application.commun.Affichage;
import org.simu.dd5.simulateur.application.commun.ToStringPretty;
import org.simu.dd5.simulateur.application.round.RoundService;
import org.simu.dd5.simulateur.domaine.opposant.Antagonistes;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.resultat.ResultatPlusieursCombat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChargementController {
    private static final Logger logger = LoggerFactory.getLogger(ChargementController.class);

    private final ChargementService chargementService;
    private final RoundService roundService;
    private final ToStringPretty toStringPretty;
    private final ChoixAttaque choixAttaque;
    private final Affichage affichage;

    private List<Opposant> opposantListe;
    private List<Antagonistes> antagonistesListe;

	// TODO : gérer les attaques multiples
	// TODO : gérer les incantations
    @GetMapping("/charge")
    public void chargement() {
        opposantListe = chargementService.chargeOpposants();

        Opposant opposantA = opposantListe.stream().filter(o -> o.getNom().equals("Quasit")).findFirst().orElse(null);

        System.out.println(opposantA);
    }

    @GetMapping("/combat/{nom1}/{nom2}")
    public ResultatPlusieursCombat attaqueDeDeuxOpposants(@PathVariable String nom1, @PathVariable String nom2) throws Exception {
        if (opposantListe == null || opposantListe.isEmpty()) {
            opposantListe = chargementService.chargeOpposants();
        }

        Opposant opposantA = opposantListe.stream().filter(o -> o.getNom().equals(nom1)).findFirst().orElse(null);
        Opposant opposantB = opposantListe.stream().filter(o -> o.getNom().equals(nom2)).findFirst().orElse(null);

        return roundService.lancePlusieursCombats(opposantA, opposantB, 30);
    }

    @GetMapping("/combat/aleatoires")
    public void attaquesAleatoires() throws Exception {
        if (opposantListe == null || opposantListe.isEmpty()) {
            opposantListe = chargementService.chargeOpposants();
        }

        opposantListe = opposantListe
                .stream()
                .filter(Opposant::aAuMoinsUneAttaque)
                .toList();

        if (antagonistesListe == null || antagonistesListe.isEmpty()) {
            antagonistesListe = new ArrayList<>();
            for (int i = 0; i < opposantListe.size(); i++) {
                for (int j = 0; j < i; j++) {
                    antagonistesListe.add(new Antagonistes(opposantListe.get(i), opposantListe.get(j)));
                }
            }
        }

        Collections.shuffle(antagonistesListe);

        int compteur = 0;
        for (int i = 0; i < 5; i++) { // nombre de saisons de combats
			System.out.println("Saison " + i);
            for (Antagonistes antagonistes : antagonistesListe) {
                roundService.lancePlusieursCombats(antagonistes.getOpposantA(), antagonistes.getOpposantB(), 1);
                compteur += 1;
            }
        }

        toStringPretty.save_complet(opposantListe);

        // analyse
        Collections.shuffle(antagonistesListe);

        XYSeries series = new XYSeries("Données (x, y)");

        for (Opposant o : opposantListe) {
            series.add(o.getDangerosite(), o.getClassementELO());
        }

        // Appliquer la régression logarithmique
        LogarithmicRegression regression = new LogarithmicRegression();
        //regression.fit(opposantListe);
        regression.fit(opposantListe);

        // Série pour la courbe logarithmique prédite
        XYSeries regressionSeries = new XYSeries("Régression Logarithmique");
        // Générer des points prédits sur une plage de x
        double minX = opposantListe.stream().mapToDouble(Opposant::getDangerosite).min().orElse(1);
        double maxX = opposantListe.stream().mapToDouble(Opposant::getDangerosite).max().orElse(1);

        for (double x = minX; x <= maxX; x += 0.1) {
            //double predictedY = regression.predict(x); // Utiliser la régression pour prédire y
            double predictedY = regression.predict(x); // Utiliser la régression pour prédire y
            System.out.println(x);
            System.out.println(predictedY);
            regressionSeries.add(x, predictedY);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(regressionSeries);  // Ajouter la courbe de régression

        // Créer un graphique XY (chart)
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                "Graphique XY",           // Titre du graphique
                "Dangerosité",                      // Légende de l'axe X
                "ELO",                      // Légende de l'axe Y
                dataset,                  // Les données
                PlotOrientation.VERTICAL,
                true,                     // Inclure la légende
                true,                     // Inclure des tooltips
                false                     // Pas de liens URL
        );

        // Enregistrer le graphique sous forme d'image PNG
        try {
            File imageFile = new File("GraphiqueXY.png");
            ChartUtils.saveChartAsPNG(imageFile, xyLineChart, 800, 600);
            System.out.println("Graphique enregistré : " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du graphique : " + e.getMessage());
        }

        Map<Integer, OutlierDetection.OutlierResult> outlierResults = OutlierDetection.findOutliersByDangerosite(opposantListe, regression, 2.0);

        // Afficher les résultats
        for (Map.Entry<Integer, OutlierDetection.OutlierResult> entry : outlierResults.entrySet()) {
            Integer dangerosite = entry.getKey();
            OutlierDetection.OutlierResult result = entry.getValue();

            System.out.println("Dangerosité: " + dangerosite);

            System.out.println("Outliers:");
            for (Opposant outlier : result.outliers) {
                System.out.println(" - " + outlier.getNom() + " : y = " + outlier.getClassementELO());
            }

            System.out.println("Points normaux:");
            for (Opposant normal : result.normalPoints) {
                System.out.println(" - " + normal.getNom() + " : y = " + normal.getClassementELO());
            }
        }
    }

    @GetMapping("/creature/{nomCreature}")
    public String getCreature(@PathVariable String nomCreature) {
        if (opposantListe == null || opposantListe.isEmpty()) {
            opposantListe = chargementService.chargeOpposants();
        }

        Opposant opposant = opposantListe.stream().filter(o -> o.getNom().equalsIgnoreCase(nomCreature)).findFirst().orElse(null);

        return toStringPretty.cleanJsonFromObject(opposant);
    }

    @GetMapping("/creatures/save")
    public void saveCreatures() {
        if (opposantListe == null || opposantListe.isEmpty()) {
            opposantListe = chargementService.chargeOpposants();
        }

        toStringPretty.save_complet(opposantListe);
    }
}
