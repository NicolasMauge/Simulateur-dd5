package org.simu.dd5.simulateur.application.analyse;

import lombok.Getter;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;

import java.util.List;

@Getter
public class LogarithmicRegression {
    private double a, b;
    private double range;
    private double min;
    private double max;

    public void fit(List<Opposant> data) {
        int n = data.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (Opposant point : data) {
            double x = point.getDangerosite(); // Utilisez la dangerosité d'origine
            double y = point.getClassementELO();

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        // Calcul des coefficients a et b
        a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        b = (sumY - a * sumX) / n;

        // Vérification si a ou b sont NaN
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new RuntimeException("Les paramètres de régression ne peuvent pas être NaN.");
        }
    }

    public double predict(double x) {
        return a * x + b; // Prédiction basée sur la régression linéaire
    }
}
