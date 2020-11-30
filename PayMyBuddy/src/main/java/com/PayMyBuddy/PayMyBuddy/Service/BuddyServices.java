package com.PayMyBuddy.PayMyBuddy.Service;

import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.BuddyAccountDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Entities.User;
import com.PayMyBuddy.PayMyBuddy.Exceptions.BuddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BuddyServices {

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RelationDAO relationDAO;

    @Autowired
    private AccountDAO accountDAO;

    public boolean addBuddy(String mailUser , String mailBuddy) throws BuddyException {


        //Vérifier que le buddy est bien enregistré dans le système.
        try {
            if (connexionDAO.findById(mailBuddy).get().getUser() == null) {
                throw new BuddyException("Buddy inconnu");
            }
        } catch (BuddyException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }

        //Verifier que la relation n'est pas déja existante
        try {
            List<Relation> lRelation = connexionDAO.findById(mailUser).get().getUser().getUserList();
            for (Relation rel:lRelation) {
                if (rel.getUser() == connexionDAO.findById(mailUser).get().getUser()
                        && rel.getUserBuddy() == connexionDAO.findById(mailBuddy).get().getUser())
                {
                    throw new BuddyException("Cette utilisateur est déja dans la liste d'ami");
                }
            }

        } catch (BuddyException e) {
            e.printStackTrace();
            return false;
        }


        Relation relation = new Relation();

        relation.setUser(connexionDAO.findById(mailUser).get().getUser());

        relation.setUserBuddy(connexionDAO.findById(mailBuddy).get().getUser());

        relation.setDateBeginning(LocalDate.now());
        relation.setDateEnd(null); //pas de date de fin

        relationDAO.save(relation);

        return true;

    }

    public List<BuddyAccountDTO> listBuddyById(String email){

        List<BuddyAccountDTO> lBuddyAccount = new ArrayList<>();

        User userId = connexionDAO.findById(email).get().getUser();

        List<Relation> lRelation = relationDAO.findAllByUser(userId);

        for (Relation rel : lRelation) {

            BuddyAccountDTO buddyAccountDTO = new BuddyAccountDTO();

            buddyAccountDTO.setEmailBuddy(connexionDAO.findConnexionByUserId(rel.getUserBuddy().getId()).getEmail());
            buddyAccountDTO.setFirstName(rel.getUserBuddy().getFirstName());
            buddyAccountDTO.setLastName(rel.getUserBuddy().getLastName());

            lBuddyAccount.add(buddyAccountDTO);
        }

        return lBuddyAccount;


    }
}
