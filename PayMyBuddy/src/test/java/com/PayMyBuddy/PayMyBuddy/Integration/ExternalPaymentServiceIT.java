package com.PayMyBuddy.PayMyBuddy.Integration;

import com.PayMyBuddy.PayMyBuddy.Config.DataBasePreparation;
import com.PayMyBuddy.PayMyBuddy.DAO.AccountDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.ExternalAccountDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.ExternalAccountDTO;
import com.PayMyBuddy.PayMyBuddy.DTO.PaymentDTO;
import com.PayMyBuddy.PayMyBuddy.Service.ExternalAccountServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class ExternalPaymentServiceIT {

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ExternalAccountDAO externalAccountDAO;

    @Autowired
    private ExternalAccountServices externalAccountServices;

    @Autowired
    private DataBasePreparation dataBasePreparation;

    @Test
    public void AccountPaymentTest() {

        dataBasePreparation.setUp();

        PaymentDTO payment1 = new PaymentDTO();

        payment1.setEmail("Essai1@Test");
        payment1.setAmount(500);
        payment1.setDescription("Essaie de virement");

        externalAccountServices.paymentToExternalAccount(payment1);

        assertThat(accountDAO.findAccountByUser(connexionDAO.findById("Essai1@Test").get().getUser()).getBalance()).isEqualTo(4499.75);


    }

    @Test
    public void addExternalAccount() {

        dataBasePreparation.setUp();

        ExternalAccountDTO externalAccountDTO = new ExternalAccountDTO();

        externalAccountDTO.setEmail("Essai2@Test");
        externalAccountDTO.setBankName("TestBank");
        externalAccountDTO.setIBAN("FRXXXXXXXXXXX");
        externalAccountDTO.setBIC("ABCDEF");

        externalAccountServices.addExternalAccount(externalAccountDTO);

        assertThat(externalAccountDAO.findExternalAccountByUser(connexionDAO.findById("Essai2@Test").get().getUser()).getBankName()).isEqualTo("TestBank");
        assertThat(externalAccountDAO.findExternalAccountByUser(connexionDAO.findById("Essai2@Test").get().getUser()).getIBAN()).isEqualTo("FRXXXXXXXXXXX");
        assertThat(externalAccountDAO.findExternalAccountByUser(connexionDAO.findById("Essai2@Test").get().getUser()).getBIC()).isEqualTo("ABCDEF");


    }
}
