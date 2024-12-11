package ru.osipovmaksim.BurgerApp.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ReceptId implements Serializable {
    private Burger burger;
    private Ingredient ingredient;
}