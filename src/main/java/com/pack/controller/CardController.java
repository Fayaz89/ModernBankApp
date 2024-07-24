package com.pack.controller;

import com.pack.exception.CardAlreadyExistsException;
import com.pack.exception.CardNotFoundException;
import com.pack.model.Card;
import com.pack.response.ResponseHandler;
import com.pack.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Object>  issueCard(@RequestBody Card card) throws CardAlreadyExistsException {
    	return ResponseHandler.generateResponse("Card issued successfully", HttpStatus.CREATED,
				 cardService.issueCard(card));
    }

    @GetMapping
    public ResponseEntity<Object> getAllCards() {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK, cardService.getAllCards());
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<Object> getCardById(@PathVariable String cardNumber) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.getCardById(cardNumber));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getCardById(@PathVariable int userId) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.getCardByUserId(userId));
    }

    @PutMapping("/{cardNumber}")
    public ResponseEntity<Object> updateCard(@PathVariable String cardNumber, @RequestBody Card cardDetails) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.updateCard(cardNumber, cardDetails));
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<Object> deleteCard(@PathVariable String cardNumber) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.deleteCard(cardNumber));
    }

    @PutMapping("/{cardNumber}/activate")
    public ResponseEntity<Object> activateCard(@PathVariable String cardNumber) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.activateCard(cardNumber));
    }

    @PutMapping("/{cardNumber}/deactivate")
    public ResponseEntity<Object> deactivateCard(@PathVariable String cardNumber) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.deactivateCard(cardNumber));
    }

    @PutMapping("/{cardNumber}/hold")
    public ResponseEntity<Object> holdCard(@PathVariable String cardNumber) throws CardNotFoundException {
        return ResponseHandler.generateResponse("Retrieved all cards", HttpStatus.OK,cardService.holdCard(cardNumber));
    }
}
