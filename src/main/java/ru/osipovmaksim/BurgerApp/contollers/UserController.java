package ru.osipovmaksim.BurgerApp.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.osipovmaksim.BurgerApp.entity.Burger;
import ru.osipovmaksim.BurgerApp.repository.BurgerRepository;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final BurgerRepository burgerRepository;
    @GetMapping("/burgers")
    public List<Burger> getBurgers() {
        return burgerRepository.findAll();
    }
}
