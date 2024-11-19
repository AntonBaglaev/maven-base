package org.lesson4.seminar;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.lesson4.seminar.AbstractTest.getSession;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditTest extends AbstractTest{

    @Order(1)
    @Test
    void addValue() {
        //given
        CreditEntity entity = new CreditEntity();
        entity.setCreditId((short) 10);
        entity.setBalance("1000");
        entity.setCloseDate("2033-02-01 00:00:00");
        entity.setOpenDate("2033-02-01 00:00:00");
        entity.setNumber("100");
        entity.setSumm("1000000");
        entity.setStatus("Open");
        entity.setClient((short) 1);
        entity.setEmployee((short) 1);
        //then
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM credit WHERE credit_id="+10)
                .addEntity(CreditEntity.class);
        CreditEntity creditEntity = (CreditEntity) query.uniqueResult();

        //then
        Assertions.assertNotNull(creditEntity);
    }

    @Order(2)
    @Test
    void deleteTest() {
        //given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM credit WHERE credit_id=" + 10)
                .addEntity(CreditEntity.class);
        Optional<CreditEntity> creditEntity = (Optional<CreditEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(creditEntity.isPresent());
        //when
        Session session = getSession();
        session.beginTransaction();
        session.delete(creditEntity.get());
        session.getTransaction().commit();
        //then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM credit WHERE credit_id=" + 10)
                .addEntity(CreditEntity.class);
        Optional<CreditEntity> creditEntityAfterDelete = (Optional<CreditEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(creditEntityAfterDelete.isPresent());
    }

}
