package com.PayMyBuddy.PayMyBuddy.Config;

import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;


@Component
@Transactional
public class DataBasePreparation {

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private RelationDAO relationDAO;

    //@BeforeAll
    //@Test
    public void setUp(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Connexion connexion1 = new Connexion();
        User user1 = new User();

        connexion1.setEmail("Essai1@Test");
        connexion1.setPassword(passwordEncoder.encode("MDP1"));

        user1.setFirstName("Test1");
        user1.setLastName("TESTUN");
        user1.setBirthday(LocalDate.parse("1995-01-01"));
        user1.setAdress("Rue du premier");
        user1.setCity("PARIS");
        user1.setZipCode(12345);

        //Sauvegarde
        userDAO.save(user1);
        connexion1.setUser(user1);
        connexionDAO.save(connexion1);

        //creation du compte dans le même temps
        Account account1 = new Account();

        account1.setDateCreated(LocalDate.now());
        account1.setStatus("Active");
        account1.setBalance(0);
        account1.setUser(user1);

        accountDAO.save(account1);


        //User2
        Connexion connexion2 = new Connexion();
        User user2 = new User();

        connexion2.setEmail("Essai2@Test");
        connexion2.setPassword(passwordEncoder.encode("MDP2"));

        user2.setFirstName("Test2");
        user2.setLastName("TESTDEUX");
        user2.setBirthday(LocalDate.parse("2002-12-31"));
        user2.setAdress("Rue du second");
        user2.setCity("Marseille");
        user2.setZipCode(23456);

        //Sauvegarde
        userDAO.save(user2);
        connexion2.setUser(user2);
        connexionDAO.save(connexion2);

        //creation du compte dans le même temps
        Account account2 = new Account();

        account2.setDateCreated(LocalDate.now());
        account2.setStatus("Active");
        account2.setBalance(0);
        account2.setUser(user2);

        accountDAO.save(account2);

        //User 3
        Connexion connexion3 = new Connexion();
        User user3 = new User();

        connexion3.setEmail("Essai3@Test");
        connexion3.setPassword(passwordEncoder.encode("MDP3"));

        user3.setFirstName("Test3");
        user3.setLastName("TESTTROIS");
        user3.setBirthday(LocalDate.parse("1980-12-31"));
        user3.setAdress("Rue du troisième");
        user3.setCity("Brive-La-Gaillarde");
        user3.setZipCode(34567);

        //Sauvegarde
        userDAO.save(user3);
        connexion3.setUser(user3);
        connexionDAO.save(connexion3);

        //creation du compte dans le même temps
        Account account3 = new Account();

        account3.setDateCreated(LocalDate.now());
        account3.setStatus("Active");
        account3.setBalance(0);
        account3.setUser(user3);

        accountDAO.save(account3);

        //Création des buddies
        Relation relation = new Relation();
        relation.setUser(connexionDAO.findById("Essai1@Test").get().getUser());
        relation.setUserBuddy(connexionDAO.findById("Essai2@Test").get().getUser());
        relation.setDateBeginning(LocalDate.now());
        relation.setDateEnd(null);
        relationDAO.save(relation);

        Relation relation2 = new Relation();
        relation2.setUser(connexionDAO.findById("Essai1@Test").get().getUser());
        relation2.setUserBuddy(connexionDAO.findById("Essai3@Test").get().getUser());
        relation2.setDateBeginning(LocalDate.now());
        relation2.setDateEnd(null);
        relationDAO.save(relation2);

        //Alimentation des soldes

        User user = connexionDAO.findById("Essai1@Test").get().getUser();
        Account account = accountDAO.findAccountByUser(user);
        account.setBalance(5000);
        accountDAO.save(account);

    }
}
