package com.dd5.model.combat;

import com.dd5.entity.protagoniste.ProtagonisteEntity;

public record PaireAttaquantDefenseur(
        ProtagonisteEntity attaquant,
        ProtagonisteEntity defenseur) {
}
