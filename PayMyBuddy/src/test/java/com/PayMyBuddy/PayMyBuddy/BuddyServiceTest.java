package com.PayMyBuddy.PayMyBuddy;

import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Exceptions.BuddyException;
import com.PayMyBuddy.PayMyBuddy.Service.BuddyServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;


public class BuddyServiceTest {

    @InjectMocks
    private BuddyServices buddyServices = new BuddyServices();

    @Mock
    private  ConnexionDAO connexionDAO;

    @Mock
    private  UserDAO userDAO;

    @Mock
    private RelationDAO relationDAO;

    @Mock
    private  AccountDAO accountDAO;

    @Test
    public void buddyNotRegistredTest() throws BuddyException {

        MockitoAnnotations.initMocks(this);

        assertFalse(buddyServices.addBuddy("Essai1@Test","Essai4@Test"));
    }


    @Test
    public void AlreadyBuddyTest() throws BuddyException {

        MockitoAnnotations.initMocks(this);

        User user1 = new User();

        user1.setFirstName("Test1");
        user1.setLastName("TESTUN");
        user1.setBirthday(LocalDate.parse("1995-01-01"));
        user1.setAdress("Rue du premier");
        user1.setCity("PARIS");
        user1.setZipCode(12345);

        Connexion connexion1 = new Connexion();
        connexion1.setEmail("Essai1@Test");
        connexion1.setPassword("MDP1");
        connexion1.setUser(user1);

        User user2 = new User();

        Connexion connexion2 = new Connexion();
        connexion2.setEmail("Essai2@Test");
        connexion2.setPassword("MDP2");
        connexion2.setUser(user2);

        User user3 = new User();

        Connexion connexion3 = new Connexion();
        connexion3.setEmail("Essai3@Test");
        connexion3.setPassword("MDP3");
        connexion3.setUser(user3);

        Relation rel1 = new Relation();
        Relation rel2 = new Relation();
        Relation rel3 = new Relation();

        rel1.setUser(user1);
        rel1.setUserBuddy(user3);

        List<Relation> lRelation = new ArrayList<Relation>();

        lRelation.add(rel1);

        user1.setUserList(lRelation);

        when(connexionDAO.findById("Essai1@Test")).thenReturn(Optional.of(connexion1));
        when(connexionDAO.findById("Essai2@Test")).thenReturn(Optional.of(connexion2));
        when(connexionDAO.findById("Essai3@Test")).thenReturn(Optional.of(connexion3));

        assertFalse(buddyServices.addBuddy("Essai1@Test","Essai3@Test"));


    }
}
