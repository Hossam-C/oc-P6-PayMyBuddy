package com.PayMyBuddy.PayMyBuddy.Service;

import com.PayMyBuddy.PayMyBuddy.Constants.FeesTypeValue;
import com.PayMyBuddy.PayMyBuddy.Constants.MovementType;
import com.PayMyBuddy.PayMyBuddy.DAO.*;
import com.PayMyBuddy.PayMyBuddy.DTO.ExternalAccountDTO;
import com.PayMyBuddy.PayMyBuddy.DTO.PaymentDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.*;
import com.PayMyBuddy.PayMyBuddy.Exceptions.PaymentException;
import com.PayMyBuddy.PayMyBuddy.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;


@Service
@Transactional
public class ExternalAccountServices {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private MovementsDAO movementsDAO;

    @Autowired
    private ExternalMovementsDAO externalMovementsDAO;

    @Autowired
    private ExternalAccountDAO externalAccountDAO;

    public boolean paymentToExternalAccount(PaymentDTO paymentDTO) {

        try {
            if (connexionDAO.findById(paymentDTO.getEmail()).get().getUser() == null) {
                throw new UserException("User inconnu");
            }
        } catch (UserException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }

        double amountUserBefore;
        double amountUserAfter;

        Connexion connexion = connexionDAO.findById(paymentDTO.getEmail()).get();

        Account account = accountDAO.findAccountByUser(connexion.getUser());

        amountUserBefore =  account.getBalance();

        //Récupération du taux de frais et calcul des montants
        double feesValue = FeesTypeValue.EXT_PAYMENT_VALUE * paymentDTO.getAmount();


        //Verification que le compte de l'utilisateur a un solde suffisant (montant du transfert + frais)
        try {
            if (amountUserBefore - paymentDTO.getAmount() - feesValue < 0) {
                throw new PaymentException("Solde insuffisant");
            }
        } catch (PaymentException e) {
            e.printStackTrace();
            return false;
        }


        amountUserAfter = amountUserBefore - paymentDTO.getAmount() - feesValue;

        account.setBalance(amountUserAfter);


        accountDAO.save(account);

        //ecriture du mouvement du compte exterieur
        ExternalMovements externalMovements = new ExternalMovements();

        externalMovements.setMovementType(MovementType.ALIM_EXT_ACC);
        externalMovements.setIssuer(connexion.getUser().getId()) ;
        externalMovements.setBeneficiary(connexion.getUser().getId());
        externalMovements.setDateMovement(LocalDate.now());
        externalMovements.setDescription(paymentDTO.getDescription());
        externalMovements.setAccount(account);

        externalMovementsDAO.save(externalMovements);


        return true;
    }

    public boolean AddExternalAccount ( ExternalAccountDTO externalAccountDTO) {

        ExternalAccount externalAccount = new ExternalAccount();
        ExternalMovements externalMovements = new ExternalMovements();



        //Vérification que le user est bien en base

        User user = connexionDAO.findById(externalAccountDTO.getEmail()).get().getUser();

        try {
            if (user == null) {
                throw new UserException("User inconnu");
            }
        } catch (UserException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }

        //Alimentation des entités et enregistrement

        //External Account
        externalAccount.setIBAN(externalAccountDTO.getIBAN());
        externalAccount.setBIC(externalAccountDTO.getBIC());
        externalAccount.setBankName(externalAccountDTO.getBankName());
        externalAccount.setDateAdded(LocalDate.now());
        externalAccount.setUser(user);

        externalAccountDAO.save(externalAccount);

        //External Movement
        externalMovements.setMovementType(MovementType.CREATE_EXT_ACC);
        externalMovements.setBeneficiary(user.getId());
        externalMovements.setDateMovement(LocalDate.now());
        externalMovements.setDescription("Add external account");

        Account account = accountDAO.findAccountByUser(user);
        externalMovements.setAccount(account);

        externalMovements.setExternalAccount(externalAccount);

        externalMovementsDAO.save(externalMovements);

        return true;
    }
}
