package ru.osipovmaksim.BurgerApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.BurgerApp.config.SecurityConfig;
import ru.osipovmaksim.BurgerApp.dto.UserDto;
import ru.osipovmaksim.BurgerApp.entity.Role;
import ru.osipovmaksim.BurgerApp.entity.User;
import ru.osipovmaksim.BurgerApp.entity.Usersrole;
import ru.osipovmaksim.BurgerApp.exception.UserAlreadyExistsException;
import ru.osipovmaksim.BurgerApp.repository.RoleRepository;
import ru.osipovmaksim.BurgerApp.repository.UserRepository;
import ru.osipovmaksim.BurgerApp.repository.UsersroleRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final UsersroleRepository usersroleRepository;
    private final RoleRepository roleRepository;

    public void registrationNewUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.username()).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с таким username уже сущетсвует!\n" +
                    "Попробуйте ввести другой.");
        } else {
            System.out.println("Есть контакт в сервисе");
            User user = new User();
            user.setName(userDto.name());
            user.setPassword(securityConfig.passwordEncoder().encode(userDto.password()));
            user.setUsername(userDto.username());
            user.setTelephone(userDto.telephone());
            userRepository.save(user);
            Usersrole usersrole = new Usersrole();
            Role role = roleRepository.findByName("USER");
            usersrole.setRole(role);
            usersrole.setUser(user);
            usersroleRepository.save(usersrole);
        }
    }
}
