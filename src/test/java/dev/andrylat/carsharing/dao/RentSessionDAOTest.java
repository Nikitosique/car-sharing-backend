package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.RentSession;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:initalscripts/dao/rentsession/RentSessionTableCreation.sql",
        "classpath:initalscripts/dao/rentsession/RentSessionDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/rentsession/RentSessionTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RentSessionDAOTest {
    @Autowired
    private RentSessionDAO rentSessionDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/rentsession/RentSessionDataDeletion.sql"})
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0;
        long actual = rentSessionDAO.getRecordsNumber();
        assertEquals(expected, actual);
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long expected = 3;
        long actual = rentSessionDAO.getRecordsNumber();
        assertEquals(expected, actual);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/rentsession/RentSessionDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<RentSession> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<RentSession> actual = rentSessionDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() throws SQLException {
        List<RentSession> expected = new ArrayList<>();

        RentSession rentSession = new RentSession();
        rentSession.setId(1);
        rentSession.setCustomerId(1);
        rentSession.setCarId(14);
        rentSession.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        rentSession.setRentSessionCost(100000);
        expected.add(rentSession);

        rentSession = new RentSession();
        rentSession.setId(2);
        rentSession.setCustomerId(2);
        rentSession.setCarId(8);
        rentSession.setRentTimeInterval(new PGInterval("15 hour 36 minutes 47 second"));
        rentSession.setRentSessionCost(1500);
        expected.add(rentSession);

        int pageNumber = 0;
        int pageSize = 2;
        List<RentSession> actual = rentSessionDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }


    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            rentSessionDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() throws SQLException {
        RentSession expected = new RentSession();
        expected.setId(1);
        expected.setCustomerId(1);
        expected.setCarId(14);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        RentSession actual = rentSessionDAO.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() throws SQLException {
        RentSession expected = new RentSession();
        expected.setId(4);
        expected.setCustomerId(145);
        expected.setCarId(99);
        expected.setRentTimeInterval(new PGInterval("10 day 15 hour 21 minutes"));
        expected.setRentSessionCost(100000);

        RentSession added = new RentSession();
        added.setCustomerId(145);
        added.setCarId(99);
        added.setRentTimeInterval(new PGInterval("10 day 15 hour 21 minutes"));
        added.setRentSessionCost(100000);

        RentSession actual = rentSessionDAO.add(added);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() throws SQLException {
        boolean expected = false;

        RentSession updatedRentSession = new RentSession();
        updatedRentSession.setId(0);
        updatedRentSession.setCustomerId(10);
        updatedRentSession.setCarId(20);
        updatedRentSession.setRentTimeInterval(new PGInterval("1 day 2 hour"));
        updatedRentSession.setRentSessionCost(2000);

        boolean actual = rentSessionDAO.updateById(updatedRentSession);

        assertEquals(expected, actual);

    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() throws SQLException {
        boolean expected = true;

        RentSession updatedRentSession = new RentSession();
        updatedRentSession.setId(1);
        updatedRentSession.setCustomerId(10);
        updatedRentSession.setCarId(20);
        updatedRentSession.setRentTimeInterval(new PGInterval("1 day 2 hour"));
        updatedRentSession.setRentSessionCost(2000);

        boolean actual = rentSessionDAO.updateById(updatedRentSession);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = rentSessionDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = rentSessionDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}