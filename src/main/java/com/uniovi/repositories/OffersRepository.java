package com.uniovi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface OffersRepository extends CrudRepository<Offer, Long> {
	@Modifying
	@Transactional
	@Query("UPDATE Offer SET buyed = ?1,buyedBy = ?3 WHERE id = ?2")
	void updateBuyed(Boolean buyed, Long id,String user);

	@Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1))")
	Page<Offer> searchByDescriptionAndName(Pageable pageable, String seachtext);

	@Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.title) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user != ?2 ")
	Page<Offer> searchByTitleDescriptionNameExceptUser(Pageable pageable, String seachtext, User user);

	@Query("SELECT r FROM Offer r WHERE r.user = ?1 ORDER BY r.id ASC ")
	Page<Offer> findAllByUser(Pageable pageable, User user);
	
	@Query("SELECT r FROM Offer r WHERE r.user = ?1 ORDER BY r.id ASC ")
	List<Offer> findAllByUserNoPageable(User user);
	
	@Query("SELECT r FROM Offer r WHERE (r.buyedBy = ?1 AND r.buyed = true) ORDER BY r.id ASC ")
	Page<Offer> findAllBuyedByUser(Pageable pageable, String user);
	
	@Query("SELECT r FROM Offer r WHERE (r.buyedBy = ?1 AND r.buyed = true) ORDER BY r.id ASC ")
	List<Offer> findAllBuyedByUserNoPageable(String user);
	
	@Query("SELECT r FROM Offer r WHERE r.user != ?1 ORDER BY r.id ASC ")
	Page<Offer> findAllExceptUser(Pageable pageable, User user);

	Page<Offer> findAll(Pageable pageable);

}
