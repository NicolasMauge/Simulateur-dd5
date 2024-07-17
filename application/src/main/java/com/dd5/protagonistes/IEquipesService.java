package com.dd5.protagonistes;

import com.dd5.protagoniste.Equipes;

import java.util.Optional;

public interface IEquipesService {
    Equipes save(Equipes equipes);
    Optional<Equipes> findById(Long id);
    Equipes reconstruireEquipes(Equipes equipes);
}
