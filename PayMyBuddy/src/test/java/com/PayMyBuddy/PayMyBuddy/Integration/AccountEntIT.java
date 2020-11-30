package com.PayMyBuddy.PayMyBuddy.Integration;


import com.PayMyBuddy.PayMyBuddy.Config.DataBasePreparation;
import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AccountEntIT {

    @Autowired
    private DataBasePreparation dataBasePreparation;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private RelationDAO relationDAO;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void accountAddTest() {

        dataBasePreparation.setUp();

        Account account = accountDAO.findAccountByUser(connexionDAO.findById("Essai1@Test").get().getUser());

        assertThat(account.getBalance()).isEqualTo(5000);
        assertThat(account.getDateCreated()).isEqualTo(LocalDate.now());
        assertThat(account.getStatus()).isEqualTo("Active");
    }
}
