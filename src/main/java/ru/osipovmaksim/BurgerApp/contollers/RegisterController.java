package ru.osipovmaksim.BurgerApp.contollers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.osipovmaksim.BurgerApp.config.MvcConfig;
import ru.osipovmaksim.BurgerApp.dto.UserDto;
import ru.osipovmaksim.BurgerApp.entity.User;
import ru.osipovmaksim.BurgerApp.repository.UserRepository;
import ru.osipovmaksim.BurgerApp.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
public class RegisterController {

    private final RegistrationService registrationService;
    @PostMapping
    public ResponseEntity<String> registrationNewUser(@RequestBody UserDto userDto) {
        System.out.println("контроллер");
        registrationService.registrationNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registration successfully!");
    }
}
