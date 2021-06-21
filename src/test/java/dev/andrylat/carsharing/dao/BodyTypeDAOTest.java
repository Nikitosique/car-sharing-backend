package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.BodyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"classpath:initalscripts/dao/bodytype/BodyTypeTableCreation.sql",
        "classpath:initalscripts/dao/bodytype/BodyTypeDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/bodytype/BodyTypeTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BodyTypeDAOTest {
    @Autowired
    private BodyTypeDAO bodyTypeDAO;

    @Test
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        bodyTypeDAO.deleteAll();
        List<BodyType> expected = new ArrayList<>();
        List<BodyType> actual = bodyTypeDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnAllRecords_WhenTableIsNotEmpty() {
        List<BodyType> expected = new ArrayList<>(List.of(
                new BodyType(1, "sedan"),
                new BodyType(2, "coupe"),
                new BodyType(3, "sport car")));

        List<BodyType> actual = bodyTypeDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            bodyTypeDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        BodyType expected = new BodyType(1, "sedan");
        BodyType actual = bodyTypeDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        BodyType expected = new BodyType(4, "SUV");
        BodyType actual = bodyTypeDAO.add(new BodyType(4, "SUV"));
        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldThrownException_WhenUpdatedRecordNotExists() {
        BodyType updatedBodyType = new BodyType(0, "New Type");

        assertThrows(NullPointerException.class, () -> {
            bodyTypeDAO.updateById(updatedBodyType);
        });
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        BodyType expected = new BodyType(1, "New Type");
        BodyType updatedBodyType = new BodyType(1, "New Type");
        BodyType actual = bodyTypeDAO.updateById(updatedBodyType);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = bodyTypeDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = bodyTypeDAO.deleteById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteAll_ShouldDeleteAllRecords_WhenDeletedRecordsExist() {
        boolean expected = true;
        boolean actual = bodyTypeDAO.deleteAll();
        assertEquals(expected, actual);
    }

}
