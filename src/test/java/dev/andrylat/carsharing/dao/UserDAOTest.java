package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
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
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {

        List<User> expected = new ArrayList<>();
        List<User> actual = userDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnAllRecords_WhenTableIsNotEmpty() {
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

        user = new User();
        user.setId(3);
        user.setEmail("manager1@awesomecars.com");
        user.setPassword("10w4rtEd");
        user.setType("manager");
        expected.add(user);

        List<User> actual = userDAO.getAll();
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
    public void updateById_ShouldThrownException_WhenUpdatedRecordNotExists() {
        User updated = new User();
        updated.setId(0);
        updated.setEmail("user1@gmail.com");
        updated.setPassword("Xo9eOes");
        updated.setDiscountCardId(1);
        updated.setType("customer");

        assertThrows(RecordNotFoundException.class, () -> {
            userDAO.updateById(updated);
        });
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        User expected = new User();
        expected.setId(1);
        expected.setEmail("firstUser@mail.com");
        expected.setPassword("12345678");
        expected.setDiscountCardId(1);
        expected.setType("customer");

        User updated = new User();
        updated.setId(1);
        updated.setEmail("firstUser@mail.com");
        updated.setPassword("12345678");
        updated.setDiscountCardId(1);
        updated.setType("customer");

        User actual = userDAO.updateById(updated);
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
    public void getCustomersIdByManagerId_ShouldReturnZeroRecords_WhenManagerWithSuchIdNotExists() {
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = userDAO.getCustomersIdByManagerId(0);
        assertEquals(expected, actual);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomersIdByManagerId_ShouldReturnRecords_WhenManagerWithSuchIdExists() {
        List<Integer> expected = new ArrayList<>();

        int firstCustomerId = 1;
        int secondCustomerId = 2;
        expected.add(firstCustomerId);
        expected.add(secondCustomerId);

        List<Integer> actual = userDAO.getCustomersIdByManagerId(3);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"classpath:initalscripts/dao/user/CustomerManagerTableCreation.sql",
            "classpath:initalscripts/dao/user/CustomerManagerDataInsertion.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:initalscripts/dao/user/CustomerManagerTableDropping.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void assignCustomerToManager_ShouldAddRecord_WhenDataAreInserted() {
        int customerId = 4;
        int managerId = 10;

        boolean expected = true;
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
        int customerId = 0;
        int managerId = 0;

        boolean expected = false;
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
        int customerId = 1;
        int managerId = 3;

        boolean expected = true;
        boolean actual = userDAO.unassignCustomerFromManager(customerId, managerId);
        assertEquals(expected, actual);
    }

}