package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("""
        SELECT c FROM Client c
        WHERE c.document = :document
        AND (:isDeleted is null OR c.isDeleted = :isDeleted)
    """)
    Optional<Client> findClientByDocument(@Param("document") String document, @Param("isDeleted") Boolean isDeleted);


    @Query("""
        SELECT c FROM Client c
        WHERE c.isDeleted = false
    """)
    List<Client> findAllActiveClients();

}
