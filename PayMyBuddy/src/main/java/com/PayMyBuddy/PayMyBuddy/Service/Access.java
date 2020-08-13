package com.PayMyBuddy.PayMyBuddy.Service;

import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.Entities.Connexion;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class Access {

    private static final Logger logger = LogManager.getLogger(Access.class);


    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    public boolean createAccount(Connexion connexion, User user)  {

        userDAO.save(user);
        connexionDAO.save(connexion);


        return true;

    }


    public boolean loginAccount(Connexion connexion){

        Optional<Connexion> connexionDemand = Optional.of(new Connexion());
        connexionDemand = connexionDAO.findById(connexion.getEmail());


        if ( connexionDemand.get().getPassword().equals(connexion.getPassword()))
            {
            return true;
        }
        else {
            return false;
        }
    }

}
