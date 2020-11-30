package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.ExternalAccount;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ExternalAccountDAO extends JpaRepository<ExternalAccount, Integer> {

    public ExternalAccount findExternalAccountByUser(User user);
}
