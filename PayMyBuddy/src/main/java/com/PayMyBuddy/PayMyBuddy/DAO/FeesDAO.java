package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.ExternalMovements;
import com.PayMyBuddy.PayMyBuddy.Entities.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface FeesDAO extends JpaRepository<Fees, Integer> {

    public List<Fees> findAllByExternalMovements(ExternalMovements externalMovements);
}
