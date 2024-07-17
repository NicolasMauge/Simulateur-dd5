package com.dd5.endpoints;

import com.dd5.combat.ICombatService;
import com.dd5.combat.TousLesProtagonistes;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagonistes.EquipesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class CombatController {
    private final ICombatService combatService;
    private final EquipesService equipesService;

    public CombatController(ICombatService combatService, EquipesService equipesService) {
        this.combatService = combatService;
        this.equipesService = equipesService;
    }

    @PostMapping("/attaque")
    public TousLesProtagonistes lanceCombat(@RequestBody Equipes equipe) {
        return combatService.commenceCombat(equipesService.reconstruireEquipes(equipe));
    }
}
