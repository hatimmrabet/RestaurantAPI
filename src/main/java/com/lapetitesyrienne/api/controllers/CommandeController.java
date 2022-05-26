package com.lapetitesyrienne.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.lapetitesyrienne.api.models.Commande;
import com.lapetitesyrienne.api.models.User;
import com.lapetitesyrienne.api.models.request.CommandeRequest;
import com.lapetitesyrienne.api.models.response.CommandeDTO;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.CommandeRepository;
import com.lapetitesyrienne.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @GetMapping()
    public ResponseEntity<?> getAllCommandes() {
        List<CommandeDTO> commandes = commandeRepository.findAll().stream().map(commande -> new CommandeDTO(commande)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commandes);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommandeByID(@PathVariable String id) {
        if (commandeRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(commandeRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Commande not found"));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getCommandeByClient(@PathVariable String id) {
        User u = userRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(commandeRepository.findByClient(u));
    }

    @PostMapping()
    public ResponseEntity<?> createCommande(@Valid @RequestBody CommandeRequest request) {
        String numero = request.getType()+"1";
        User client;
        Commande lastCmd = commandeRepository.findTopByOrderByDateDesc();
        if(lastCmd != null) {
            int num = Integer.parseInt(lastCmd.getNumero().substring(lastCmd.getType().length())) + 1 ;
            numero = request.getType() + num;
        }
        if(userRepository.findById(request.getClient().getId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("User not found"));
        }
        client = userRepository.findById(request.getClient().getId()).get();
        // i deleted menu from commande
        Commande cmd = new Commande(numero, new Date(), "En cours", request.getType(), client, request.getItems());
        cmd.countPrice();
        commandeRepository.save(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommandeDTO(cmd));
    }
    
    
}
