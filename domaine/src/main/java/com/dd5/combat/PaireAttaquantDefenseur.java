package com.dd5.combat;

import com.dd5.protagoniste.ProtagonisteEntity;

public record PaireAttaquantDefenseur(
        ProtagonisteEntity attaquant,
        ProtagonisteEntity defenseur) {
}
