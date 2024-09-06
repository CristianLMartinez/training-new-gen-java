package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByFantasyName(String name);

    Optional<Product> findProductByUuid(UUID id);

    boolean existsByFantasyName(String fantasyName);

    List<Product> findByAvailable(boolean available);

    @Query("""
            SELECT p FROM Product p 
            WHERE LOWER(p.fantasyName) 
            LIKE LOWER(CONCAT('%', :fantasyName, '%')) 
            ORDER BY p.fantasyName ASC
            """)
    List<Product> searchByFantasyName(String fantasyName);

}
