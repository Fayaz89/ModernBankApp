package com.pack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.model.Card;


@Repository
public interface CardRepository extends JpaRepository<Card, String>{

	Optional<Card> findByUserId(int userId);
	List<Card> findByMasterId(int masterId);
}
