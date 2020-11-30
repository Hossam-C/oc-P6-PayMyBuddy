package com.PayMyBuddy.PayMyBuddy.Service;

import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.ConnexionDTO;
import com.PayMyBuddy.PayMyBuddy.DTO.UserDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Account;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Exceptions.ConnexionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Transactional
public class Access {

    private static final Logger logger = LogManager.getLogger(Access.class);


    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccountDAO accountDAO;

    public boolean createAccount(UserDTO userDTO)  {

        //Vérification que le mail n'est pas déja utilisé (mail unique)
        //Recherche d'une connexion

        try {
            if ( connexionDAO.findById(userDTO.getEmail()).isPresent()) {
                throw new ConnexionException("Cet eMail : "+userDTO.getEmail()+ " est déja existant");
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
            return false ;
        }


        //Vérification que toutes les données obligatoires sont la pour la connexion et la création du compte
        try {
            if (userDTO.getEmail().isEmpty()) {
                throw new ConnexionException("L'adresse eMail est absente");
            }
            if (userDTO.getPassword().isEmpty()) {
                throw new ConnexionException("Le mot de passe est absent");
            }
            if (userDTO.getFirstName().isEmpty()) {
                throw new ConnexionException("Le prénom est absent");
            }
            if (userDTO.getLastName().isEmpty()) {
                throw new ConnexionException("Le nom est absent");
            }
            if (userDTO.getBirthday() == null) {
                throw new ConnexionException("La date de naissance est absente");
            }
            if (userDTO.getAdress().isEmpty()) {
                throw new ConnexionException("L'adresse est absente");
            }
            if (userDTO.getCity().isEmpty()) {
                throw new ConnexionException("La ville est absente");
            }
            if (userDTO.getZipCode() == 0) {
                throw new ConnexionException("Le code postal est absent");
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
            return false;
        }

        // Alimentation des Entités
        Connexion connexion = new Connexion();
        User user = new User();

        connexion.setEmail(userDTO.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        connexion.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthday(userDTO.getBirthday());
        user.setAdress(userDTO.getAdress());
        user.setCity(userDTO.getCity());
        user.setZipCode(userDTO.getZipCode());

        //Sauvegarde
        userDAO.save(user);
        connexion.setUser(user);
        connexionDAO.save(connexion);

        //creation du compte dans le même temps
        Account account = new Account();

        account.setDateCreated(LocalDate.now());
        account.setStatus("Active");
        account.setBalance(0);
        account.setUser(user);

        accountDAO.save(account);

        return true;

    }


    public boolean loginAccount(ConnexionDTO connexionDTO) throws ConnexionException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        //Recherche de l'entité Connexion correspondant à la demande
        Connexion connexionDemand;


        //Verification que le compte de connexion existe
        try {
            connexionDemand = connexionDAO.findById(connexionDTO.getEmail()).get();
            if (connexionDemand == null) {
                throw new ConnexionException("Identifiant non reconnu");
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }

        //Vérification que le mot de passe est le bon

        try {
            //if (connexionDemand.getPassword().equals(passwordEncoder. (connexionDTO.getPassword()))) {
            if (passwordEncoder.matches(connexionDTO.getPassword(),connexionDemand.getPassword())) {
                return true;
            } else {
                throw new ConnexionException("Mot de passe incorrect");
               // return false;
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
