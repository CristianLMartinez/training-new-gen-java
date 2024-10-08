package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.entity.Client;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("""
                    SELECT c FROM Client c
                    WHERE c.document = :document
                    AND (:isDeleted is null OR c.isDeleted = :isDeleted)
            """)
    Optional<Client> findClientByDocument(@Param("document") String document, @Param("isDeleted") Boolean isDeleted);

    default Optional<Client> findClientByDocument(String document) {
        return findClientByDocument(document, null);
    }

}
