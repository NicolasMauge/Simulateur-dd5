package com.dd5.combat;

import com.dd5.entity.protagoniste.Equipes;
import com.dd5.model.combat.PaireAttaquantDefenseur;

import java.util.List;

public interface IPaireService {
    List<PaireAttaquantDefenseur> getPaireAttaquantDefenseur(Equipes equipes);
}
