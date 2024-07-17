package com.dd5.combat;

import com.dd5.protagoniste.Equipes;

import java.util.List;

public interface IPaireService {
    List<PaireAttaquantDefenseur> getPaireAttaquantDefenseur(Equipes equipes);
}
