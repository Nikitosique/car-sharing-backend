package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.FuelType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:initalscripts/dao/fueltype/FuelTypeTableCreation.sql",
        "classpath:initalscripts/dao/fueltype/FuelTypeDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/fueltype/FuelTypeTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FuelTypeDAOTest {
    @Autowired
    private FuelTypeDAO fuelTypeDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/fueltype/FuelTypeDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<FuelType> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<FuelType> actual = fuelTypeDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() {
        List<FuelType> expected = new ArrayList<>(List.of(
                new FuelType(1, "gasoline"),
                new FuelType(2, "natural gas")));

        int pageNumber = 0;
        int pageSize = 2;
        List<FuelType> actual = fuelTypeDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            fuelTypeDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        FuelType expected = new FuelType(1, "gasoline");
        FuelType actual = fuelTypeDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        FuelType expected = new FuelType(4, "electricity");
        FuelType actual = fuelTypeDAO.add(new FuelType(4, "electricity"));
        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() {
        boolean expected = false;

        FuelType updatedFuelType = new FuelType(0, "New Type");
        boolean actual = fuelTypeDAO.updateById(updatedFuelType);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        boolean expected = true;

        FuelType updatedFuelType = new FuelType(1, "New Type");
        boolean actual = fuelTypeDAO.updateById(updatedFuelType);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = fuelTypeDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = fuelTypeDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}