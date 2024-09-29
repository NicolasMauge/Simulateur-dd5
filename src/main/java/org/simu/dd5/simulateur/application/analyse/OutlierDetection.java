package org.simu.dd5.simulateur.application.analyse;

import org.simu.dd5.simulateur.domaine.opposant.Opposant;

import java.util.*;
import java.util.stream.Collectors;

public class OutlierDetection {
    public static class OutlierResult {
        public List<Opposant> outliers;
        public List<Opposant> normalPoints;

        public OutlierResult(List<Opposant> outliers, List<Opposant> normalPoints) {
            this.outliers = outliers;
            this.normalPoints = normalPoints;
        }
    }

    public static Map<Integer, OutlierResult> findOutliersByDangerosite(List<Opposant> data, LogarithmicRegression regression, double threshold) {
        Map<Integer, OutlierResult> results = new HashMap<>();

        // Récupérer toutes les dangerosités uniques
        Set<Integer> dangerosites = data.stream().map(Opposant::getDangerosite).collect(Collectors.toSet());

        // Pour chaque dangerosité unique, calculer les outliers et les points normaux
        for (Integer dangerosite : dangerosites) {
            List<Opposant> filteredData = data.stream()
                    .filter(o -> o.getDangerosite() == dangerosite)
                    .collect(Collectors.toList());

            OutlierResult outlierResult = findOutliers(filteredData, regression, threshold);
            results.put(dangerosite, outlierResult);
        }

        return results;
    }

    public static OutlierResult findOutliers(List<Opposant> data, LogarithmicRegression regression, double threshold) {
        List<Opposant> outliers = new ArrayList<>();
        List<Opposant> normalPoints = new ArrayList<>();
        double sumSquaredErrors = 0;
        double meanError = 0;

        // Calcul de l'erreur moyenne
        for (Opposant point : data) {
            if(point.getDangerosite() > 8 && point.getDangerosite() < 140) {
                double predictedY = regression.predict(point.getDangerosite());
                double error = point.getClassementELO() - predictedY;
                meanError += error;
                sumSquaredErrors += error * error;
            }
        }

        meanError /= data.size();
        double variance = sumSquaredErrors / data.size();
        double stdDev = Math.sqrt(variance); // Ecart-type

        // Détection des outliers et des points normaux
        for (Opposant point : data) {
            if(point.getDangerosite() > 8 && point.getDangerosite() < 140) {
                double predictedY = regression.predict(point.getDangerosite());
                double error = point.getClassementELO() - predictedY;
                if (Math.abs(error - meanError) > threshold * stdDev) {
                    outliers.add(point); // Si l'erreur dépasse le seuil
                } else {
                    normalPoints.add(point); // Sinon, c'est un point normal
                }
            }
        }

        return new OutlierResult(outliers, normalPoints);
    }
}
