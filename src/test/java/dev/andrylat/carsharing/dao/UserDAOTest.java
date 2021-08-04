package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:initalscripts/dao/user/UserTableCreation.sql",
        "classpath:initalscripts/dao/user/UserDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/user/UserTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/user/UserDataDeletion.sql"})
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0;
        long actual = userDAO.getRecordsNumber();
        assertEquals(expected, actual);
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long expected = 3;
        long actual = userDAO.getRecordsNumber();
        assertEquals(expected, actual);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/user/UserDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<User> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<User> actual = userDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() {
        List<User> expected = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setPassword("3NreW8R");
        user.setDiscountCardId(1);
        user.setType("customer");
        expected.add(user);

        user = new User();
        user.setId(2);
        user.setEmail("user2@gmail.com");
        user.setPassword("2345qwerty");
        user.setDiscountCardId(2);
        user.setType("customer");
        expected.add(user);

        int pageNumber = 0;
        int pageSize = 2;
        List<User> actual = userDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }


    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        User expected = new User();
        expected.setId(1);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1);
        expected.setType("customer");

        User actual = userDAO.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        User expected = new User();
        expected.setId(4);
        expected.setEmail("user4@gmail.com");
        expected.setPassword("C04jq9b");
        expected.setDiscountCardId(4);
        expected.setType("customer");

        User added = new User();
        added.setEmail("user4@gmail.com");
        added.setPassword("C04jq9b");
        added.setDiscountCardId(4);
        added.setType("customer");

        User actual = userDAO.add(added);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() {
        boolean expected = false;

        User updatedUser = new User();
        updatedUser.setId(0);
        updatedUser.setEmail("user1@gmail.com");
        updatedUser.setPassword("Xo9eOes");
        updatedUser.setDiscountCardId(1);
        updatedUser.setType("customer");

        boolean actual = userDAO.updateById(updatedUser);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        boolean expected = true;

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("firstUser@mail.com");
        updatedUser.setPassword("12345678");
        updatedUser.setDiscountCardId(1);
        updatedUser.setType("customer");

        boolean actual = userDAO.updateById(updatedUser);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = userDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = userDAO.deleteById(1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomersNumberByManagerId_ShouldReturnZero_WhenManagerWithSuchIdNotExists() {
        long expected = 0;

        long managerId = 0;

        long actual = userDAO.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomersNumberByManagerId_ShouldReturnNumber_WhenManagerWithSuchIdNotExists() {
        long expected = 2;

        long managerId = 3;

        long actual = userDAO.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomersIdByManagerId_ShouldReturnZeroRecords_WhenManagerWithSuchIdNotExists() {
        List<Long> expected = new ArrayList<>();

        long managerId = 0;
        int pageNumber = 0;
        int pageSize = 2;

        List<Long> actual = userDAO.getCustomersIdByManagerId(managerId, pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomersIdByManagerId_ShouldReturnFirstPageWithRecords_WhenManagerWithSuchIdExists() {
        List<Long> expected = new ArrayList<>();

        long firstCustomerId = 1;
        long secondCustomerId = 2;
        expected.add(firstCustomerId);
        expected.add(secondCustomerId);

        long managerId = 3;
        int pageNumber = 0;
        int pageSize = 2;

        List<Long> actual = userDAO.getCustomersIdByManagerId(managerId, pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void assignCustomerToManager_ShouldAddRecord_WhenDataAreInserted() {
        boolean expected = true;

        long customerId = 4;
        long managerId = 10;

        boolean actual = userDAO.assignCustomerToManager(customerId, managerId);

        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void unassignCustomerFromManager_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;

        long customerId = 0;
        long managerId = 0;

        boolean actual = userDAO.unassignCustomerFromManager(customerId, managerId);

        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void unassignCustomerFromManager_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;

        long customerId = 1;
        long managerId = 3;

        boolean actual = userDAO.unassignCustomerFromManager(customerId, managerId);

        assertEquals(expected, actual);
    }

}