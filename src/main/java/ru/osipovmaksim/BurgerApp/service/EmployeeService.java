package ru.osipovmaksim.BurgerApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.BurgerApp.dto.EmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.request.RequestEmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseEmployeeDto;
import ru.osipovmaksim.BurgerApp.entity.Order;
import ru.osipovmaksim.BurgerApp.repository.EmployeeRepository;
import ru.osipovmaksim.BurgerApp.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    public List<ResponseEmployeeDto> getEmployees(){
        return employeeRepository.findAllEmployees();
    }

    public void assignEmployeeForOrder(int orderId,
                                       RequestEmployeeDto requestEmployeeDto){
        orderRepository.updateEmployeeById(employeeRepository.findById(requestEmployeeDto.employeeId()).get(),orderId);
    }
}
