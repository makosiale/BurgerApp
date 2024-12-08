package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.osipovmaksim.BurgerApp.dto.EmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseEmployeeDto;
import ru.osipovmaksim.BurgerApp.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT new ru.osipovmaksim.BurgerApp.dto.response.ResponseEmployeeDto(e.id, CONCAT(e.name, ' ', e.surname, ' ', e.patronymic)) FROM Employee e")
    List<ResponseEmployeeDto> findAllEmployees();


    Optional<Employee> findById(int integer);
}