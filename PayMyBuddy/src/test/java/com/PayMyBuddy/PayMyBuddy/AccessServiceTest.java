package com.PayMyBuddy.PayMyBuddy;


import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.ConnexionDTO;
import com.PayMyBuddy.PayMyBuddy.DTO.UserDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Exceptions.ConnexionException;
import com.PayMyBuddy.PayMyBuddy.Service.AccessServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AccessServiceTest {

    @InjectMocks
    private AccessServices accessServices = new AccessServices();

    @Mock
    private ConnexionDAO connexionDAO;

    @Test
    public void createAccountExistingEmailTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();



        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Connexion connexion = new Connexion();
        Optional<Connexion> notEmptyConnection = Optional.of(connexion);

        when(connexionDAO.findById(anyString())).thenReturn(notEmptyConnection);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingEmailTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingPasswordTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingFirstNameTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingLastNameTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingBirthDateTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        //userDTO.setBirthday("");
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingAdressTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void createAccountMissingCityTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("");
        userDTO.setZipCode(45678);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }
    @Test
    public void createAccountMissingZipCodeTest() {

        MockitoAnnotations.initMocks(this);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("Essai4@Test");
        userDTO.setPassword("MDP4");
        userDTO.setFirstName("Test4");
        userDTO.setLastName("TESTQUATRE");
        userDTO.setBirthday(LocalDate.parse("1980-10-10"));
        userDTO.setAdress("Rue du quatrième");
        userDTO.setCity("Strasbourg");
        userDTO.setZipCode(0);
        Optional emptyConnexion = Optional.empty();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.createAccount(userDTO));

    }

    @Test
    public void loginAccountNotExistingAccount() throws ConnexionException {

        MockitoAnnotations.initMocks(this);

        ConnexionDTO connexionDTO = new ConnexionDTO();

        connexionDTO.setEmail("Essai4@Test");
        connexionDTO.setPassword("MDP4");

        Optional emptyConnexion = Optional.empty();
        Connexion connexion = new Connexion();

        when(connexionDAO.findById(anyString())).thenReturn(emptyConnexion);

        assertFalse(accessServices.loginAccount(connexionDTO));

    }

}
