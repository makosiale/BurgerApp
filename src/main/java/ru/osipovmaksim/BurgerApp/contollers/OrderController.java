package ru.osipovmaksim.BurgerApp.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.osipovmaksim.BurgerApp.dto.request.RequestOrderDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseUserOrderListDto;
import ru.osipovmaksim.BurgerApp.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public List<ResponseUserOrderListDto> getAllOrders(@RequestParam String username) {
        System.out.println(username);
        return orderService.getUserOrders(username);
    }


    @PostMapping
    public void saveOrderFromUser(@RequestBody RequestOrderDto requestOrderDto) {
        orderService.saveOrderFromUser(requestOrderDto);
    }
}
