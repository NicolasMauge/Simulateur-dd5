package com.dd5.entity.attaque;

import com.dd5.entity.testdifficulte.AbstractConfrontation;
import com.dd5.entity.effets.AbstractEffetEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SubAttaque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "common_object_id")
    private AbstractConfrontation test;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AbstractEffetEntity> effets;
}
