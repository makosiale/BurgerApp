package ru.osipovmaksim.BurgerApp.service;


import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.BurgerApp.dto.request.RequestAddBurgerDto;
import ru.osipovmaksim.BurgerApp.entity.Burger;
import ru.osipovmaksim.BurgerApp.entity.Recept;
import ru.osipovmaksim.BurgerApp.repository.BurgerRepository;
import ru.osipovmaksim.BurgerApp.repository.IngredientRepository;
import ru.osipovmaksim.BurgerApp.repository.ReceptRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BurgerService {


    private final BurgerRepository burgerRepository;
    private final ReceptRepository receptRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    @Builder
    public void addNewBurger(RequestAddBurgerDto requestAddBurgerDto) {
        Burger burger = burgerRepository.save(Burger
                .builder()
                .cost(BigDecimal.valueOf(requestAddBurgerDto.cost()))
                .name(requestAddBurgerDto.name()).build()
        );

        System.out.println(requestAddBurgerDto);
        for (RequestAddBurgerDto.ReceptBurgerDto receptBurgerDto : requestAddBurgerDto.ingredients()) {
            receptRepository.save(Recept.builder()
                    .burger(burger)
                    .ingredient(ingredientRepository.findById(receptBurgerDto.ingredientId()))
                    .quantity(receptBurgerDto.quantity())
                    .build()
            );
        }
    }
}
