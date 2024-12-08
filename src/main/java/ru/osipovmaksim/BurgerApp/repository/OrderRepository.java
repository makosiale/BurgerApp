package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.osipovmaksim.BurgerApp.dto.OrderDto;
import ru.osipovmaksim.BurgerApp.dto.request.RequestOrderDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseOrderListDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseOrderSummaryDto;
import ru.osipovmaksim.BurgerApp.dto.response.ResponseUserOrderListDto;
import ru.osipovmaksim.BurgerApp.entity.Employee;
import ru.osipovmaksim.BurgerApp.entity.Order;
import ru.osipovmaksim.BurgerApp.entity.view.OrderSummaryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Query(nativeQuery = true, value = "SELECT o.order_id AS id,\n" +
            "       JSON_AGG(\n" +
            "           JSON_BUILD_OBJECT(\n" +
            "               'burgerName', b.name,\n" +
            "               'quantity', ol.count\n" +
            "           )\n" +
            "       ) AS positions\n" +
            "FROM orders o\n" +
            "JOIN orderslist ol ON o.order_id = ol.order_id\n" +
            "JOIN Burgers b ON ol.burger_id = b.burger_id\n" +
            "JOIN users u ON u.user_id = o.user_id\n" +
            "WHERE u.username = :username\n" +
            "GROUP BY o.order_id\n" +
            "ORDER BY o.order_id")
    List<Object[]> findAllOrdersByUser(@Param("username") String username);


    @Query(value = "SELECT * FROM order_summary", nativeQuery = true)
    List<Object[]> findAllOrdersSummary();



    @Query(value = "CALL create_order_with_burgers(:username, :burgersJson)", nativeQuery = true)
    void addUserOrder(@Param("username") String username, @Param("burgersJson") String burgersJson);

    @Override
    Optional<Order> findById(Integer integer);

    @Transactional
    @Modifying
    @Query("update Order o set o.employee = ?1 where o.id = ?2")
    int updateEmployeeById(Employee employee, Integer id);

    @Query(value = "SELECT* FROM getTotalRevenue()", nativeQuery = true)
    int getTotalRevenue();
}