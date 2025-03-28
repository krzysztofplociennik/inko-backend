package com.plociennik.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "AUTOCOMPLETE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutocompleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String value;

    private int numberOfUses;
}
