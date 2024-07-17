package com.dd5.protagonistes;

import com.dd5.combat.TousLesProtagonistes;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.ProtagonisteEntity;

import java.util.List;

public interface IProtagonisteService {
    ProtagonisteEntity save(ProtagonisteEntity protagonisteEntity);
    List<ProtagonisteEntity> findAll();
    ProtagonisteEntity findById(Long id);
    TousLesProtagonistes initialiseTousLesProtagonistes(Equipes equipes);
}
