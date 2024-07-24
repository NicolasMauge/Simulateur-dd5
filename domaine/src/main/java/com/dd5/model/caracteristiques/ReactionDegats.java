package com.dd5.model.caracteristiques;

import java.util.List;

public record ReactionDegats(
        List<Resistance> listeResistances,
        List<Vulnerabilite> listeVulnerabilites,
        List<Immunite> listeImmunites) {
}
