package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.osipovmaksim.BurgerApp.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    @Query(value = "select * from ingredients where name = :name", nativeQuery = true)
    Optional<Ingredient> findByName(@Param("name") String name);

    //    @Procedure(procedureName = "update_ingredient_residue")
    @Query(value = "call update_ingredient_residue(:id, :residue)", nativeQuery = true)
    void updateIngredientResidue(int id, int residue);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ingredients(name, residue) VALUES (:name, :residue)")
    void saveIngredient(@Param("name") String name, @Param("residue") int residue);


    @Query(value = "select * from ingredients where ingredient_id = :id", nativeQuery = true)
    Ingredient findById(int id);
}