package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface MovementsDAO extends JpaRepository<Movements, Integer> {
}
