package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.ExternalMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ExternalMovementsDAO extends JpaRepository<ExternalMovements, Integer> {
}
