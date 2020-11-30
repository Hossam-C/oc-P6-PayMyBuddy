package com.PayMyBuddy.PayMyBuddy.Integration;

import com.PayMyBuddy.PayMyBuddy.Config.DataBasePreparation;
import com.PayMyBuddy.PayMyBuddy.DAO.ConnexionDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.RelationDAO;
import com.PayMyBuddy.PayMyBuddy.DAO.UserDAO;
import com.PayMyBuddy.PayMyBuddy.DTO.BuddyAccountDTO;
import com.PayMyBuddy.PayMyBuddy.Entities.Relation;
import com.PayMyBuddy.PayMyBuddy.Exceptions.BuddyException;
import com.PayMyBuddy.PayMyBuddy.Service.Access;
import com.PayMyBuddy.PayMyBuddy.Service.BuddyServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class BuddyServiceIT {


    @Autowired
    private Access access;

    @Autowired
    private BuddyServices buddyServices;

    @Autowired
    private RelationDAO relationDAO;

    @Autowired
    private ConnexionDAO connexionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DataBasePreparation dataBasePreparation;


    @Test
    public void ajoutBuddyTest() throws BuddyException {

        dataBasePreparation.setUp();

        buddyServices.addBuddy("Essai2@Test","Essai3@Test");

        List <Relation> relationList = relationDAO.findAllByUser(connexionDAO.findById("Essai1@Test").get().getUser());

        assertThat(relationList.get(0).getUserBuddy().getId()).isEqualTo(connexionDAO.findById("Essai2@Test").get().getUser().getId());
        assertThat(relationList.get(1).getUserBuddy().getId()).isEqualTo(connexionDAO.findById("Essai3@Test").get().getUser().getId());

    }

    @Test
    public void listBuddyTest() throws BuddyException {

        dataBasePreparation.setUp();

        List<BuddyAccountDTO> lBuddy = buddyServices.listBuddyById("Essai1@Test");

        assertThat(lBuddy.size()).isEqualTo(2);
        assertThat(lBuddy.get(0).getEmailBuddy()).isEqualTo("Essai2@Test");
        assertThat(lBuddy.get(0).getFirstName()).isEqualTo("Test2");
        assertThat(lBuddy.get(0).getLastName()).isEqualTo("TESTDEUX");

        assertThat(lBuddy.get(1).getEmailBuddy()).isEqualTo("Essai3@Test");
        assertThat(lBuddy.get(1).getFirstName()).isEqualTo("Test3");
        assertThat(lBuddy.get(1).getLastName()).isEqualTo("TESTTROIS");

    }
}
