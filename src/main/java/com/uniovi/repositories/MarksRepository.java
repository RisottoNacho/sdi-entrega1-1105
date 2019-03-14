package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface MarksRepository extends CrudRepository<Offer, Long> {
	@Modifying
	@Transactional
	@Query("UPDATE Mark SET resend = ?1 WHERE id = ?2")
	void updateResend(Boolean resend, Long id);

	@Query("SELECT r FROM Mark r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1))")
	Page<Offer> searchByDescriptionAndName(Pageable pageable, String seachtext);

	@Query("SELECT r FROM Mark r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user = ?2 ")
	Page<Offer> searchByDescriptionNameAndUser(Pageable pageable, String seachtext, User user);

	@Query("SELECT r FROM Mark r WHERE r.user = ?1 ORDER BY r.id ASC ")
	Page<Offer> findAllByUser(Pageable pageable, User user);

	Page<Offer> findAll(Pageable pageable);

}
