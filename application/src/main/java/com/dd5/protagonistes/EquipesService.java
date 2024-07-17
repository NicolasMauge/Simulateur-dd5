package com.dd5.protagonistes;

import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.EquipesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipesService {
    private final EquipesRepository equipesRepository;
    private final ProtagonisteService protagonisteService;

    public EquipesService(EquipesRepository equipesRepository, ProtagonisteService protagonisteService) {
        this.equipesRepository = equipesRepository;
        this.protagonisteService = protagonisteService;
    }

    public Equipes save(Equipes equipes) {
        return equipesRepository.save(equipes);
    }

    public Optional<Equipes> findById(Long id) {
        return equipesRepository.findById(id);
    }

    public Equipes reconstruireEquipes(Equipes equipes) {
        equipes.setEquipeA(equipes.getEquipeA()
                                        .stream()
                                        .map(p -> protagonisteService.findById(p.getId()))
                                        .toList());

        equipes.setEquipeB(equipes.getEquipeB()
                .stream()
                .map(p -> protagonisteService.findById(p.getId()))
                .toList());

        return equipes;
    }
}
