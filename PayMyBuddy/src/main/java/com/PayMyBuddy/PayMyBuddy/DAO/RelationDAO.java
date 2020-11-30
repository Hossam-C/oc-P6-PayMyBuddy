package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface RelationDAO extends JpaRepository<Relation, Integer> {

    public List<Relation> findAllByUser(User user);

}
