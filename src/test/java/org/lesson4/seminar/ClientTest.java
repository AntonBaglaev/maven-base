package org.lesson4.seminar;

import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientTest extends AbstractTest{

    @Test
    void getCountTest() throws SQLException {
        //given
        String sql = "Select * from client";
        Statement stmt = getConnection().createStatement();
        int count = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count ++;
        }

        //then
        Assertions.assertEquals(3, count);
    }

    @Test
    void getCountTestORM() {
        //given
        //when
        final Query query = getSession().createSQLQuery("Select * from client")
                .addEntity(ClientTest.class);
        //then
        Assertions.assertEquals(3, query.getResultList().size());
    }

    @ParameterizedTest
    @CsvSource({"1, Иванов", "2, Петров", "3, Сидоров"})
    void testName(int id, String name) throws SQLException {
        //given
        String sql = "Select * from client where client_id=" + id;
        Statement statement = getConnection().createStatement();
        String nameResult = "";
        //when
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            nameResult = rs.getString(3);
        }
        //then
        Assertions.assertEquals(name, nameResult);
    }
}