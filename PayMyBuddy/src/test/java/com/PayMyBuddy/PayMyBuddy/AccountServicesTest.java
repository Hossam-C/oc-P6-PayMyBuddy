package com.PayMyBuddy.PayMyBuddy;

import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.PaymentDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Service.AccountServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class AccountServicesTest {

    @InjectMocks
    private AccountServices accountServices = new AccountServices();

    @Mock
    private ConnexionDAO connexionDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    private RelationDAO relationDAO;

    @Mock
    private AccountDAO accountDAO;

    @Test
    public void paymentNotBuddy(){

        MockitoAnnotations.initMocks(this);

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setEmail("Essai1@Test");
        paymentDTO.setAmount(5000);
        paymentDTO.setDescription("Test paymentNoBuddy");

        User user1 = new User();

        Connexion connexion1 = new Connexion();
        connexion1.setEmail("Essai1@Test");
        connexion1.setPassword("MDP1");
        connexion1.setUser(user1);

        Account account1 = new Account();
        account1.setUser(user1);
        account1.setStatus("ACTIF");
        account1.setBalance(10000);

        user1.setAccounts(asList(account1));

        User user2 = new User();

        Connexion connexion2 = new Connexion();
        connexion2.setEmail("Essai2@Test");
        connexion2.setPassword("MDP2");
        connexion2.setUser(user2);

        Account account2 = new Account();
        account2.setUser(user2);
        account2.setStatus("ACTIF");
        account2.setBalance(10000);

        user2.setAccounts(asList(account2));

        User user3 = new User();

        Connexion connexion3 = new Connexion();
        connexion3.setEmail("Essai3@Test");
        connexion3.setPassword("MDP3");
        connexion3.setUser(user3);

        Account account3 = new Account();
        account3.setUser(user3);
        account3.setStatus("ACTIF");
        account3.setBalance(10000);

        user3.setAccounts(asList(account3));

        //Pas de création de liens

        when(connexionDAO.findById("Essai1@Test")).thenReturn(Optional.of(connexion1));
        when(connexionDAO.findById("Essai2@Test")).thenReturn(Optional.of(connexion2));
        when(connexionDAO.findById("Essai3@Test")).thenReturn(Optional.of(connexion3));

        when(accountDAO.findAccountByUser(user1)).thenReturn(account1);
        when(accountDAO.findAccountByUser(user2)).thenReturn(account2);
        when(accountDAO.findAccountByUser(user3)).thenReturn(account3);

        assertFalse(accountServices.paymentToBuddy(paymentDTO,"Essai2@Test"));

    }

    @Test
    public void paymentInsufficientBalance(){

        MockitoAnnotations.initMocks(this);

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setEmail("Essai1@Test");
        paymentDTO.setAmount(5000);
        paymentDTO.setDescription("Test paymentNoBuddy");

        User user1 = new User();

        Connexion connexion1 = new Connexion();
        connexion1.setEmail("Essai1@Test");
        connexion1.setPassword("MDP1");
        connexion1.setUser(user1);

        Account account1 = new Account();
        account1.setUser(user1);
        account1.setStatus("ACTIF");
        account1.setBalance(100);

        user1.setAccounts(asList(account1));

        User user2 = new User();

        Connexion connexion2 = new Connexion();
        connexion2.setEmail("Essai2@Test");
        connexion2.setPassword("MDP2");
        connexion2.setUser(user2);

        Account account2 = new Account();
        account2.setUser(user2);
        account2.setStatus("ACTIF");
        account2.setBalance(10000);

        user2.setAccounts(asList(account2));

        User user3 = new User();

        Connexion connexion3 = new Connexion();
        connexion3.setEmail("Essai3@Test");
        connexion3.setPassword("MDP3");
        connexion3.setUser(user3);

        Account account3 = new Account();
        account3.setUser(user3);
        account3.setStatus("ACTIF");
        account3.setBalance(10000);

        user3.setAccounts(asList(account3));


        Relation rel1 = new Relation();
        Relation rel2 = new Relation();
        Relation rel3 = new Relation();

        rel1.setUser(user1);
        rel1.setUserBuddy(user2);

        List<Relation> lRelation = new ArrayList<Relation>();

        lRelation.add(rel1);

        user1.setUserList(lRelation);

        when(connexionDAO.findById("Essai1@Test")).thenReturn(Optional.of(connexion1));
        when(connexionDAO.findById("Essai2@Test")).thenReturn(Optional.of(connexion2));
        when(connexionDAO.findById("Essai3@Test")).thenReturn(Optional.of(connexion3));

        when(accountDAO.findAccountByUser(user1)).thenReturn(account1);
        when(accountDAO.findAccountByUser(user2)).thenReturn(account2);
        when(accountDAO.findAccountByUser(user3)).thenReturn(account3);

        when(relationDAO.findAllByUser(user1)).thenReturn(lRelation);

        assertFalse(accountServices.paymentToBuddy(paymentDTO,"Essai2@Test"));

    }
}
