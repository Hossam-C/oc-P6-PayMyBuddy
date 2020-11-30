package com.PayMyBuddy.PayMyBuddy.Integration;


import com.PayMyBuddy.PayMyBuddy.Config.DataBasePreparation;
import com.PayMyBuddy.PayMyBuddy.DAO.*;
import com.PayMyBuddy.PayMyBuddy.DTO.PaymentDTO;
import com.PayMyBuddy.PayMyBuddy.Exceptions.BuddyException;
import com.PayMyBuddy.PayMyBuddy.Service.AccountServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class PaymentServiceIT {


    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private DataBasePreparation dataBasePreparation;

    @Test
    public void AccountPaymentTest() throws BuddyException {

        dataBasePreparation.setUp();

        PaymentDTO payment1 = new PaymentDTO();

        payment1.setEmail("Essai2@Test");
        payment1.setAmount(1200);
        payment1.setDescription("Essai de virement");

        accountServices.paymentFromAccount(payment1);

        assertThat(accountDAO.findAccountByUser(connexionDAO.findById("Essai2@Test").get().getUser()).getBalance()).isEqualTo(1200);
    }

    @Test
    public void AccountPaymentToBuddyTest() throws BuddyException {

        dataBasePreparation.setUp();

        PaymentDTO payment2 = new PaymentDTO();

        payment2.setEmail("Essai1@Test");
        payment2.setAmount(350);
        payment2.setDescription("Essai virement de Essai1@Test vers Essai3@Test");

        accountServices.paymentToBuddy(payment2,"Essai3@Test");

        assertThat(accountDAO.findAccountByUser(connexionDAO.findById("Essai3@Test").get().getUser()).getBalance()).isEqualTo(350);
        assertThat(accountDAO.findAccountByUser(connexionDAO.findById("Essai1@Test").get().getUser()).getBalance()).isEqualTo(4649.825);
    }
}
