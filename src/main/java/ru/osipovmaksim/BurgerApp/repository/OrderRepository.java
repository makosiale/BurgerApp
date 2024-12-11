package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.osipovmaksim.BurgerApp.entity.Employee;
import ru.osipovmaksim.BurgerApp.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Modifying
    @Query(nativeQuery = true,value = "call delete_order(:p_order_id)")
    public void deleteById(@Param("p_order_id") int p_order_id);

    @Query(nativeQuery = true, value = "SELECT * FROM get_user_orders(:usernamee)")
    List<Object[]> findAllOrdersByUser(@Param("usernamee") String usernamee);


    @Query(value = "SELECT * FROM order_summary", nativeQuery = true)
    List<Object[]> findAllOrdersSummary();


    @Query(value = "CALL create_order_with_burgers(:p_username, :p_burgersJson)", nativeQuery = true)
    void addUserOrder(@Param("p_username") String username, @Param("p_burgersJson") String burgersJson);

    @Override
    Optional<Order> findById(Integer integer);

    @Transactional
    @Modifying
    @Query("update Order o set o.employee = ?1 where o.id = ?2")
    int updateEmployeeById(Employee employee, Integer id);

    @Query(value = "SELECT* FROM getTotalRevenue()", nativeQuery = true)
    int getTotalRevenue();
}