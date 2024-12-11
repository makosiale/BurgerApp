package ru.osipovmaksim.BurgerApp.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.BurgerApp.dto.IngredientDto;
import ru.osipovmaksim.BurgerApp.dto.request.RequestSupplyDto;
import ru.osipovmaksim.BurgerApp.entity.Ingredient;
import ru.osipovmaksim.BurgerApp.exception.IngredientAlreadyExistsException;
import ru.osipovmaksim.BurgerApp.repository.IngredientRepository;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;


    @Transactional
    public void addNewIngredient(IngredientDto ingredientDto) {
        if (ingredientRepository.findByName(ingredientDto.name()).isPresent()) {
            throw new IngredientAlreadyExistsException("Такой ингредиент уже существует");
        } else {
            try {
                ingredientRepository.saveIngredient(ingredientDto.name(), ingredientDto.residue());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка при добавлении ингредиента");
            }
        }
    }

    public void supplyIngredient(int id, RequestSupplyDto supplyDto) {
        ingredientRepository.updateIngredientResidue(id, supplyDto.supplyQuantity());
        System.out.println("Ингредиент поставлен");
    }

    public List<Ingredient> ingredientList() {
        return ingredientRepository.findAll();
    }
}
