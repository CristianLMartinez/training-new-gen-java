package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> getClientByDocument(String document);

}
