package com.lapetitesyrienne.api.repository;

import java.util.List;

import com.lapetitesyrienne.api.models.Commande;
import com.lapetitesyrienne.api.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandeRepository extends MongoRepository<Commande, String> {

    Commande findByNumero(String numero);
    Boolean existsByNumero(String numero);
    List<Commande> findByClientOrderByDateDesc(User client);
    List<Commande> findByEtat(String etat);
    List<Commande> findByEtatAndClient(String etat, User client);
    Commande findTopByOrderByDateDesc();
    List<Commande> findByOrderByDateDesc();
    
}
