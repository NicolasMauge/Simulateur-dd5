package com.dd5.endpoints;

import com.dd5.protagoniste.Equipes;
import com.dd5.protagonistes.EquipesService;
import com.dd5.protagonistes.IProtagonisteService;
import com.dd5.protagoniste.ProtagonisteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProtagonisteController {
    private static final Logger logger = LoggerFactory.getLogger(ProtagonisteController.class);

    private final IProtagonisteService service;
    private final EquipesService equipesService;

    public ProtagonisteController(IProtagonisteService service, EquipesService equipesService) {
        this.service = service;
        this.equipesService = equipesService;
    }

    @PostMapping("/protagoniste/add")
    public ProtagonisteEntity ajouteProtagoniste(@RequestBody ProtagonisteEntity protagoniste) {
        return service.save(protagoniste);
    }

    @GetMapping("/protagoniste/all")
    public List<ProtagonisteEntity> getAllProtagoniste() {
        return service.findAll();
    }

    @PostMapping("/protagoniste/equipes")
    public Equipes definitEquipes(@RequestBody Equipes equipes) {
        return equipesService.save(equipes);
    }
}
