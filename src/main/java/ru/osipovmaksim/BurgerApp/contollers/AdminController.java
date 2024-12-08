package ru.osipovmaksim.BurgerApp.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.osipovmaksim.BurgerApp.dto.EmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.request.RequestEmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseEmployeeDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseOrderSummaryDto;
import ru.osipovmaksim.BurgerApp.repository.EmployeeRepository;
import ru.osipovmaksim.BurgerApp.service.EmployeeService;
import ru.osipovmaksim.BurgerApp.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OrderService orderService;
    private final EmployeeService employeeService;

    @GetMapping("/orders")
    public ResponseEntity<List<ResponseOrderSummaryDto>> getOrders(){
        System.out.println(orderService.getAllOrders());
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping("/employees")
    public ResponseEntity<List<ResponseEmployeeDto>> getEmployees(){
        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @PostMapping("/orders/{orderId}/assign")
    public void assignEmployee(
            @PathVariable int orderId,
            @RequestBody RequestEmployeeDto requestEmployeeDto){
        employeeService.assignEmployeeForOrder(orderId,requestEmployeeDto);
    }

    @GetMapping("/orders/total-revenue")
    public ResponseEntity<Map<String,Integer>> getTotalRevenue(){
        Map<String,Integer> answer = new HashMap<>();
        answer.put("totalRevenue", orderService.getTotalRevenue());
        return ResponseEntity.ok(answer);
    }
}

