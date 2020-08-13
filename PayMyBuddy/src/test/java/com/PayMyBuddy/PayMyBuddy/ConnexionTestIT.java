package com.PayMyBuddy.PayMyBuddy;

import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Service.Access;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnexionTestIT {

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private Access access;

    @Test
    public void ajoutConnexionTest() throws ParseException {



        Connexion connexion = new Connexion();
        User user = new User();

        connexion.setEmail("Test2");
        connexion.setPassword("MDP");

        user.setFirstName("Testa");
        user.setLastName("TESTAA");
        user.setBirthday(LocalDate.parse("2000-01-01"));
        user.setCity("PARIS");
        user.setZipCode(12345);

        connexion.setUser(user);

        access.createAccount(connexion,user);

    }

    @Test
    public void loginAccountTest() throws ParseException {



        Connexion connexion = new Connexion();

        connexion.setEmail("Test2");
        connexion.setPassword("MDPQ");

        assertThat(access.loginAccount(connexion)).isFalse();

        connexion.setEmail("Test2");
        connexion.setPassword("MDP");

        assertThat(access.loginAccount(connexion)).isTrue();



    }
}
