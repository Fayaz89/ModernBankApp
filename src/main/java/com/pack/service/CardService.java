package com.pack.service;

import com.pack.exception.CardAlreadyExistsException;
import com.pack.exception.CardNotFoundException;
import com.pack.model.Card;
import com.pack.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

	public CardService() {
		dateFormat.setLenient(false);
	}

	public Card issueCard(Card card) throws CardAlreadyExistsException {
		if (cardRepository.existsById(card.getCardNumber())) {
			throw new CardAlreadyExistsException("Card already exists");
		}
		validateExpiryDate(card.getExpiryDate());
		card.setStatus("ACTIVE"); // Default status when issuing a new card
		return cardRepository.save(card);
	}
	
	public List<Card> getCardByUserId(int userId) {
		Card card = cardRepository.findByUserId(userId).orElse(null);
		List<Card> list = new ArrayList<Card>();
		if (card!=null) {
			
			list = cardRepository.findByMasterId(userId);
			list.add(card);
			
		}
		return list;
	}

	public List<Card> getAllCards() {
		return cardRepository.findAll();
	}

	public Card getCardById(String cardNumber) throws CardNotFoundException {
		Card card = cardRepository.findById(cardNumber).orElse(null);
		if (card == null) {
			throw new CardNotFoundException("Card not found");

		}
		return card;
	}

	public Card updateCard(String cardNumber,Card cardDetails) throws CardNotFoundException {
		
		Card card = cardRepository.findById(cardNumber).orElse(null);
	
		if (card!=null) {
			validateExpiryDate(cardDetails.getExpiryDate());
			cardDetails.setCardNumber(cardNumber);
			return cardRepository.save(cardDetails);
		}

		throw new CardNotFoundException("Card not found");
	}

	public Card deleteCard(String cardNumber) throws CardNotFoundException {
		Card card = cardRepository.findById(cardNumber).orElse(null);
		
		if (card!=null) {
			cardRepository.delete(card);
			return card;
		}

		throw new CardNotFoundException("Card not found");
	}

	public Card activateCard(String cardNumber) throws CardNotFoundException {
		Card card = cardRepository.findById(cardNumber).orElse(null);
		if (card != null) {
			card.setStatus("ACTIVE");
			return cardRepository.save(card);
		}
		throw new CardNotFoundException("Card not found");
	}

	public Card deactivateCard(String cardNumber) throws CardNotFoundException {
		Card card = cardRepository.findById(cardNumber).orElse(null);
		if (card != null) {
			card.setStatus("INACTIVE");
			return cardRepository.save(card);
		}
		throw new CardNotFoundException("Card not found");
	}

	public Card holdCard(String cardNumber) throws CardNotFoundException {
		Card card = cardRepository.findById(cardNumber).orElse(null);
		if (card != null) {
			card.setStatus("ON_HOLD");
			return cardRepository.save(card);
		}
		throw new CardNotFoundException("Card not found");
	}

	private void validateExpiryDate(String expiryDate) {
		try {
			dateFormat.parse(expiryDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format. Please use MM/yyyy.");
		}
	}
}
