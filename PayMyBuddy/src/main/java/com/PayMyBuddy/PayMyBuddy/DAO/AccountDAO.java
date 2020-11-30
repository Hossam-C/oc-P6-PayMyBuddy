package com.PayMyBuddy.PayMyBuddy.DAO;

import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface AccountDAO extends JpaRepository<Account, Integer> {

    public  Account findAccountByUser(User user);

}
