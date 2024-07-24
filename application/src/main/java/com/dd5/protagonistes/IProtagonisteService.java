package com.dd5.protagonistes;

import com.dd5.model.combat.TousLesProtagonistes;
import com.dd5.entity.protagoniste.Equipes;
import com.dd5.entity.protagoniste.ProtagonisteEntity;

import java.util.List;

public interface IProtagonisteService {
    ProtagonisteEntity save(ProtagonisteEntity protagonisteEntity);
    List<ProtagonisteEntity> findAll();
    ProtagonisteEntity findById(Long id);
    TousLesProtagonistes initialiseTousLesProtagonistes(Equipes equipes);
}
