package com.PayMyBuddy.PayMyBuddy.Service;

import com.PayMyBuddy.PayMyBuddy.Constants.FeesTypeValue;
import com.PayMyBuddy.PayMyBuddy.Constants.MovementType;
import com.PayMyBuddy.PayMyBuddy.DAO.*;
import com.PayMyBuddy.PayMyBuddy.DTO.PaymentDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.*;
import com.PayMyBuddy.PayMyBuddy.Exceptions.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class AccountServices {



    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private MovementsDAO movementsDAO;

    @Autowired
    private RelationDAO relationDAO;

    @Autowired
    private ExternalMovementsDAO externalMovementsDAO;

    @Autowired
    private FeesDAO feesDAO;


    public boolean paymentFromAccount(PaymentDTO paymentDTO) {
        //Reception d'argent depuis l'exterieur (virement, carte bleue,...)

        double amountUserBefore;
        double amountUserAfter;


        Connexion connexion = connexionDAO.findById(paymentDTO.getEmail()).get();
        Account account = accountDAO.findAccountByUser(connexion.getUser());

        amountUserBefore =  account.getBalance();
        amountUserAfter = amountUserBefore + paymentDTO.getAmount();

        account.setBalance(amountUserAfter);

        accountDAO.save(account);

        //ecriture du mouvement du compte exterieur
        ExternalMovements externalMovements = new ExternalMovements();

        externalMovements.setMovementType(MovementType.ALIM_ACC);
        externalMovements.setIssuer(connexion.getUser().getId());
        externalMovements.setBeneficiary(connexion.getUser().getId());
        externalMovements.setDateMovement(LocalDate.now());
        externalMovements.setDescription(paymentDTO.getDescription());
        externalMovements.setAccount(account);

        externalMovementsDAO.save(externalMovements);

        //Pas de frais pour un versement depuis l'exterieur


         return true;
    }

    public boolean paymentToBuddy(PaymentDTO paymentDTO, String mailBuddy) {

        double amountUserBefore;
        double amountUserAfter;

        double amountBuddyBefore;
        double amountBuddyAfter;

        Connexion connexionUser = connexionDAO.findById(paymentDTO.getEmail()).get();
        Connexion connexionBuddy = connexionDAO.findById(mailBuddy).get();

        Account accountUser = accountDAO.findAccountByUser(connexionUser.getUser());
        Account accountBuddy = accountDAO.findAccountByUser(connexionBuddy.getUser());

        amountUserBefore = accountUser.getBalance();
        amountBuddyBefore = accountBuddy.getBalance();


        //Verification que l'emetteur et le destinataire sont bien des buddies
        try {
            User userId = connexionUser.getUser();
            List<Relation> lBuddies = relationDAO.findAllByUser(userId);
            boolean findBuddy = false;
            for (Relation rel:lBuddies){
                if (rel.getUserBuddy().getId() == connexionBuddy.getUser().getId()){
                    findBuddy = true;
                }
            }
            if (findBuddy == false){
                throw new PaymentException("Vous n'êtes pas des Buddies!");
            }
        } catch (PaymentException e) {
            e.printStackTrace();
            return false;
        }

        //Récupération du taux de frais et calcul des montants
        double feesValue = FeesTypeValue.PAYMENT_BUDDY_VALUE * paymentDTO.getAmount();

        //Verification que le compte de l'utilisateur a un solde suffisant (montant du transfert + frais)
        try {
            if (amountUserBefore - paymentDTO.getAmount() - feesValue < 0) {
                throw new PaymentException("Solde insuffisant");
            }
        } catch (PaymentException e) {
            e.printStackTrace();
            return false;
        }


        // Calcul des montants des soldes
        amountUserAfter = amountUserBefore - paymentDTO.getAmount() - feesValue; // prise en compte des frais payé par l'emetteur
        amountBuddyAfter = amountBuddyBefore + paymentDTO.getAmount();

        accountUser.setBalance(amountUserAfter);

        accountBuddy.setBalance(amountBuddyAfter);


        accountDAO.save(accountUser);
        accountDAO.save(accountBuddy);

        //Ajout de la ligne mouvement
        Movements movements = new Movements();

        movements.setMovementType(MovementType.MOVEMENT_TO_BUDDY);
        movements.setIssuer(accountUser);
        movements.setBeneficiary(accountBuddy);
        movements.setDateMovement(LocalDate.now());
        movements.setAmount(paymentDTO.getAmount());
        movements.setDescription(paymentDTO.getDescription());

        movementsDAO.save(movements);

        //Prise en compte des frais

        Fees fees = new Fees();
        fees.setFeesType(FeesTypeValue.PAYMENT_BUDDY_LB);
        fees.setRate(0.05);
        fees.setFeesAmount(feesValue);
        fees.setMovements(movements);

        feesDAO.save(fees);

        return true;
    }
}
