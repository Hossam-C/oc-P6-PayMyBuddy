package com.PayMyBuddy.PayMyBuddy.Integration;

import com.PayMyBuddy.PayMyBuddy.Config.DataBasePreparation;
import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.ConnexionDTO;
import com.PayMyBuddy.PayMyBuddy.DTO.UserDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Exceptions.ConnexionException;
import com.PayMyBuddy.PayMyBuddy.Service.Access;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class AccessServiceIT {

    @Autowired
    private Access access;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private DataBasePreparation dataBasePreparation;


    @Test
    public void loginAccountTest() throws ConnexionException {



        dataBasePreparation.setUp();

        ConnexionDTO connexionDTO = new ConnexionDTO();

        connexionDTO.setEmail("Essai1@Test");
        connexionDTO.setPassword("MDP1");

        assertThat(access.loginAccount(connexionDTO)).isTrue();

        connexionDTO.setEmail("Essai1@Test");
        connexionDTO.setPassword("MDP2");

        assertFalse(access.loginAccount(connexionDTO));

        connexionDTO.setEmail("Essai99@Test");
        connexionDTO.setPassword("MDP1");

        assertFalse(access.loginAccount(connexionDTO));


    }

    @Test
    public void createAccountTest() throws ConnexionException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        dataBasePreparation.setUp();

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);

        access.createAccount(userDTO);

        Connexion connexion = connexionDAO.findById("Essai4@Test").get();
        User     user       = connexionDAO.findById("Essai4@Test").get().getUser();
        Account  account    = accountDAO.findAccountByUser(connexion.getUser());

        assertThat(connexion.getEmail()).isEqualTo("Essai4@Test");
        assertThat(passwordEncoder.matches("Essai4@Test",connexion.getPassword()));
        //assertThat(connexion.getPassword()).isEqualTo("MDP4");

        assertThat(user.getFirstName()).isEqualTo("Test4");
        assertThat(user.getLastName()).isEqualTo("TESTQUATRE");
        assertThat(user.getBirthday()).isEqualTo(LocalDate.parse("1980-10-10"));
        assertThat(user.getAdress()).isEqualTo("Rue du quatrième");
        assertThat(user.getCity()).isEqualTo("Strasbourg");
        assertThat(user.getZipCode()).isEqualTo(45678);

        assertThat(account.getBalance()).isEqualTo(0);
        assertThat(account.getStatus()).isEqualTo("Active");
        assertThat(account.getDateCreated()).isEqualTo(LocalDate.now());






    }
}
