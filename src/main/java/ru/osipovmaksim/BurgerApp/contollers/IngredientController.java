package ru.osipovmaksim.BurgerApp.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.osipovmaksim.BurgerApp.dto.IngredientDto;
import ru.osipovmaksim.BurgerApp.dto.request.RequestSupplyDto;
import ru.osipovmaksim.BurgerApp.entity.Ingredient;
import ru.osipovmaksim.BurgerApp.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PatchMapping("/supply/{ingredientId}")
    public void supplyIngredient(
            @RequestBody RequestSupplyDto requestSupplyDto,
            @PathVariable int ingredientId) {
        ingredientService.supplyIngredient(ingredientId, requestSupplyDto);
    }

    @PostMapping("/newIngredient")
    public ResponseEntity<String> addNewIngredient(@RequestBody IngredientDto ingredientDto) {
        ingredientService.addNewIngredient(ingredientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ингредиент успешно добавлен");
    }

    @GetMapping
    public List<Ingredient> getIngredients() {
        return ingredientService.ingredientList();
    }
}
