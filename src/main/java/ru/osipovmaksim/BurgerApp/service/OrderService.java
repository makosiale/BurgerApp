package ru.osipovmaksim.BurgerApp.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.BurgerApp.dto.request.RequestOrderDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseOrderSummaryDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseUserOrderListDto;
import ru.osipovmaksim.BurgerApp.entity.Order;
import ru.osipovmaksim.BurgerApp.entity.Orderslist;
import ru.osipovmaksim.BurgerApp.entity.User;
import ru.osipovmaksim.BurgerApp.repository.BurgerRepository;
import ru.osipovmaksim.BurgerApp.repository.OrderRepository;
import ru.osipovmaksim.BurgerApp.repository.OrderslistRepository;
import ru.osipovmaksim.BurgerApp.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderslistRepository orderslistRepository;
    private final BurgerRepository burgerRepository;


    public List<ResponseUserOrderListDto> getUserOrders(String username) {
        List<Object[]> result = orderRepository.findAllOrdersByUser(username);
        return result.stream()
                .map(row -> {
                    int orderId = ((Number) row[0]).intValue(); // Преобразование ID
                    String positionsJson = (String) row[1]; // Получение JSON
                    return new ResponseUserOrderListDto(orderId, positionsJson);
                })
                .collect(Collectors.toList());
    }


    public List<ResponseOrderSummaryDto> getAllOrders() {
        List<Object[]> results = orderRepository.findAllOrdersSummary();
        List<ResponseOrderSummaryDto> orderSummaryDtos = new ArrayList<>();

        for (Object[] row : results) {
            // Преобразуем массив в DTO
            int orderId = (Integer) row[0]; // order_id
            String username = (String) row[1]; // username
            String employee = (String) row[2]; // employee (CONCAT)
            String burgersJson = (String) row[3]; // burgers (JSON)
            BigDecimal totalPrice = (BigDecimal) row[4]; // total_price (BigDecimal)

            // Преобразуем JSON burgers в List<BurgerRecordDto>
            List<ResponseOrderSummaryDto.BurgerRecordDto> burgers = parseBurgersJson(burgersJson);

            // Создаем DTO и добавляем в список
            ResponseOrderSummaryDto dto = new ResponseOrderSummaryDto(orderId, username, employee, burgers, totalPrice);
            orderSummaryDtos.add(dto);
        }

        return orderSummaryDtos;
    }

    private List<ResponseOrderSummaryDto.BurgerRecordDto> parseBurgersJson(String burgersJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<ResponseOrderSummaryDto.BurgerRecordDto>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(burgersJson, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Transactional
    public void saveOrderFromUser(RequestOrderDto requestOrderDto) {
        Optional<User> user = userRepository.findByUsername(requestOrderDto.username());
        int user_id = user.get().getId();

        Order order = new Order();
        order.setUser(userRepository.findById(user_id).get());
        orderRepository.save(order);

        List<Orderslist> ordersLists = requestOrderDto.positions().stream()
                .map(burgerDetails -> {
                    Orderslist orderList = new Orderslist();
                    orderList.setOrder(order);
                    orderList.setBurger(burgerRepository.findById(burgerDetails.burgerId()).get());
                    orderList.setCount(burgerDetails.quantity());
                    return orderList;
                })
                .collect(Collectors.toList());
        orderslistRepository.saveAll(ordersLists);
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }
}
